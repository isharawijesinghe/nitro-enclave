package com.nitro.enclave.enclave.kms;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.nitro.common.models.AWSCredential;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

/**
 * Default KMS client implementation that use provided KMSTools binary.
 * <p>
 * More info at https://github.com/aws/aws-nitro-enclaves-sdk-c/blob/main/docs/kmstool.md
 *
 */
public class DefaultKMSClient implements KMSClient {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultKMSClient.class);

	@Value("${nitro.enclave.kmstool-enclave-cli:/app/kmstool_enclave_cli}")
	private String kmsToolEnvlaveCli;

	@Value("${sprint.aws.region:us-east-1}")
	private String awsRegion;

	@Value("${sprint.aws.awsAccessKey}")
	private String awsAccessKey;

	@Value("${sprint.aws.awsSecretAccessKey}")
	private String awsSecretAccessKey;

	@Value("${sprint.aws.sessionToken}")
	private String sessionToken;



	@Override
	public String decrypt(AWSCredential credential, String content) {
		Assert.notNull(awsAccessKey, "credential accessKeyId must not be null");
		Assert.notNull(awsSecretAccessKey, "credential secretAccessKey must not be null");
		Assert.notNull(sessionToken, "credential sessionToken must not be null");
		Assert.notNull(awsRegion, "credential region must not be null");

		if (StringUtils.isEmpty(content)) {
			return content;
		}

		try {
			LOG.info("Executing kmstool_enclave_cli ({})...", this.kmsToolEnvlaveCli);

			String[] cmd = new String[] {
					this.kmsToolEnvlaveCli,
					"--region", awsRegion,
					"--aws-access-key-id", awsAccessKey,
					"--aws-secret-access-key", awsSecretAccessKey,
					"--aws-session-token", sessionToken,
					"--ciphertext", content
			};

			ProcessBuilder builder = new ProcessBuilder(Arrays.asList(cmd));
			builder.inheritIO().redirectOutput(ProcessBuilder.Redirect.PIPE);
			Process process = builder.start();

			String procOutput = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
			process.waitFor();

			LOG.debug("KMSTool output: {}", procOutput);

			return procOutput;

		} catch (Exception e) {
			LOG.error("Cannot execute " + this.kmsToolEnvlaveCli, e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
