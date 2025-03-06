package com.rfq.enclave.model;

import java.util.UUID;

import com.nitro.common.requests.EnclaveServiceRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnclaveDecryptRequestTest {

	@Test
	public void uuidTest() {
		EnclaveServiceRequest<Object> request = new EnclaveServiceRequest<>();

		Assertions.assertNotNull(request.getId());

		try {
			UUID.fromString(request.getId());
		} catch (Exception e) {
			Assertions.fail("Invalid UUID");
		}
	}

}
