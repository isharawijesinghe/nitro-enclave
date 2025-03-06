package com.nitro.enclave.utils;

import com.nitro.common.models.AWSCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.regions.internal.util.EC2MetadataUtils;

public final class AWSUtils {

	private static final Logger LOG = LoggerFactory.getLogger(AWSUtils.class);

	private AWSUtils() {

	}

	public static AWSCredential getAWSSessionToken() {
		AwsSessionCredentials awsSessionCredentials = getAwsSessionCredentials();
		if (awsSessionCredentials == null) {
			return null;
		}
		AWSCredential credential = new AWSCredential();
		credential.setAccessKeyId(awsSessionCredentials.accessKeyId());
		credential.setSecretAccessKey(awsSessionCredentials.secretAccessKey());
		credential.setSessionToken(awsSessionCredentials.sessionToken());
		credential.setRegion(getRegion());
		return credential;
	}

	public static AwsSessionCredentials getAwsSessionCredentials() {
		// Use InstanceProfileCredentialsProvider to fetch credentials
		AwsSessionCredentials sessionCredentials = null;
		try {
			// Resolve credentials and check if they include a session token
			sessionCredentials = (AwsSessionCredentials) InstanceProfileCredentialsProvider.create().resolveCredentials();
		} catch (ClassCastException e) {
			throw new IllegalStateException("The credentials resolved are not session credentials. Ensure the environment provides session-based credentials.", e);
		}
		return sessionCredentials;
	}


	public static String getRegion() {
		return EC2MetadataUtils.getInstanceInfo().getRegion();
	}

}
