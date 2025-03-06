package com.nitro.enclave.host;

import com.nitro.common.requests.EnclaveServiceRequest;
import com.nitro.common.responses.EnclaveServiceResponse;
import com.nitro.enclave.host.network.HostClient;
import com.nitro.enclave.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.type.TypeReference;

public class NitroEnclaveClient {

	private static final Logger LOG = LoggerFactory.getLogger(NitroEnclaveClient.class);

	private final JsonMapper jsonMapper;

	private final HostClient hostClient;

	public NitroEnclaveClient(JsonMapper jsonMapper, HostClient hostClient) {
		this.jsonMapper = jsonMapper;
		this.hostClient = hostClient;
	}

	public <IN, OUT> EnclaveServiceResponse<OUT> send(EnclaveServiceRequest<IN> request) {
		Assert.notNull(this.hostClient, "hostClient must not be null");
		Assert.notNull(request, "input must not be null");

		LOG.info("Sending '{}' to enclave...", request);
		byte[] requestBytes = this.jsonMapper.writeValueAsBytes(request);
		byte[] responseBytes =  this.hostClient.send(requestBytes);

		EnclaveServiceResponse<OUT> response = this.jsonMapper.readValue(responseBytes, new TypeReference<EnclaveServiceResponse<OUT>>(){});
		LOG.info("Response for '{}' received", response);

		return response;
	}

}
