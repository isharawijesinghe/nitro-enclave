package com.nitro.enclave.enclave.kms;

import com.nitro.common.models.AWSCredential;

public interface KMSClient {

	String decrypt(AWSCredential credential, String content);

}
