# Data source for current AWS account
data "aws_caller_identity" "current" {}

# ECR Repository
resource "aws_ecr_repository" "nitro_enclave_repo" {
  name                 = "nitro-enclave-repo"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

# EC2 Instance
resource "aws_instance" "nitro_instance" {
  ami           = "ami-01816d07b1128cd2d"
  instance_type = "m5.2xlarge"
  key_name      = "ishara_new"

  vpc_security_group_ids = [aws_security_group.nitro_sg.id]
  iam_instance_profile   = aws_iam_instance_profile.instance_profile.name

  connection {
    type        = "ssh"
    user        = "ec2-user"
    private_key = file("C:\\SourceCode\\RFQ\\xxxxxxx.pem")
    host        = self.public_ip
  }

  enclave_options {
    enabled = true
  }

  user_data = <<-EOF
    #!/bin/bash
    set -e

    # Update system packages
    sudo yum update -y

    # Install Docker
    sudo yum install -y docker
    sudo systemctl start docker
    sudo systemctl enable docker
    sudo usermod -aG docker ec2-user

    # Install Nitro Enclaves CLI
    sudo dnf install aws-nitro-enclaves-cli -y
    sudo dnf install aws-nitro-enclaves-cli-devel -y

    # Verify installations
    docker --version
    nitro-cli --version
  EOF

  tags = {
    Name = "NitroEnclaveInstance"
  }

  root_block_device {
    volume_type = "gp3"
    volume_size = 30
  }

  metadata_options {
    http_endpoint               = "enabled"
    http_tokens                 = "required"
    http_put_response_hop_limit = 1
  }

  monitoring = true

  timeouts {
    create = "30m"
    update = "30m"
    delete = "30m"
  }
}

# KMS Key
resource "aws_kms_key" "nitro_enclave_kms_key" {
  description = "KMS key for Nitro Enclave"
  policy      = jsonencode({
    Version = "2012-10-17",
    Id      = "key-policy",
    Statement = [{
      Sid    = "AllowRootAccountFullAccess",
      Effect = "Allow",
      Principal = {
        AWS = "*"
      },
      Action   = "kms:*",
      Resource = "*"
    }]
  })

  tags = {
    Name = "NitroEnclaveKMSKey"
  }
}

# Security Group
resource "aws_security_group" "nitro_sg" {
  name_prefix = "nitro-sg"

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "NitroEnclaveSecurityGroup"
  }
}

# IAM Role
resource "aws_iam_role" "instance_role" {
  name = "ec2-nitro-enclave-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Action = "sts:AssumeRole",
      Effect = "Allow",
      Principal = {
        Service = "ec2.amazonaws.com"
      }
    }]
  })
}

# IAM Instance Profile
resource "aws_iam_instance_profile" "instance_profile" {
  name = "ec2-nitro-enclave-instance-profile"
  role = aws_iam_role.instance_role.name
}

# IAM Policies
resource "aws_iam_policy" "ecr_access_policy" {
  name = "ECRAccessPolicy"

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Action = [
        "ecr:GetAuthorizationToken",
        "ecr:BatchCheckLayerAvailability",
        "ecr:GetDownloadUrlForLayer",
        "ecr:BatchGetImage",
        "ecr:PutImage",
        "ecr:InitiateLayerUpload",
        "ecr:UploadLayerPart",
        "ecr:CompleteLayerUpload"
      ],
      Effect   = "Allow",
      Resource = aws_ecr_repository.nitro_enclave_repo.arn
    }]
  })
}

resource "aws_iam_policy" "kms_access_policy" {
  name        = "KMSAccessPolicy"
  description = "Policy for EC2 to access KMS for encryption and decryption"

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Action = [
        "kms:Encrypt",
        "kms:Decrypt",
        "kms:GenerateDataKey"
      ],
      Effect   = "Allow",
      Resource = aws_kms_key.nitro_enclave_kms_key.arn
    }]
  })
}

# IAM Role Policy Attachments
resource "aws_iam_role_policy_attachment" "attach_ecr_policy" {
  role       = aws_iam_role.instance_role.name
  policy_arn = aws_iam_policy.ecr_access_policy.arn
}

resource "aws_iam_role_policy_attachment" "ec2_kms_access" {
  role       = aws_iam_role.instance_role.name
  policy_arn = aws_iam_policy.kms_access_policy.arn
}