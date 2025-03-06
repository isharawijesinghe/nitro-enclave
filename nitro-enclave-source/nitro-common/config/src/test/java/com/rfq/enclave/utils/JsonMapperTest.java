package com.rfq.enclave.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.nitro.common.requests.EnclaveServiceRequest;
import com.nitro.enclave.configuration.JacksonConfiguration;
import com.nitro.enclave.utils.JsonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;

@SpringBootTest(classes = JacksonConfiguration.class)
public class JsonMapperTest {

	@Autowired
	private JsonMapper jsonMapper;

	@Test
	public void serializationPrimitiveTest() {
		EnclaveServiceRequest<String> request = new EnclaveServiceRequest<>();
		request.setAction("ACTION");
		request.setData("OK");

		byte[] bytes = this.jsonMapper.writeValueAsBytes(request);

		EnclaveServiceRequest<String> readRequest = this.jsonMapper.readValue(bytes, new TypeReference<EnclaveServiceRequest<String>>() {
		});

		Assertions.assertEquals(request.getAction(), readRequest.getAction());
		Assertions.assertEquals(request.getData(), readRequest.getData());
	}

	@Test
	public void serializationBeanTest() {
		EnclaveServiceRequest<MyBean> request = new EnclaveServiceRequest<>();
		request.setAction("ACTION");

		MyBean myBean = new MyBean();
		request.setData(myBean);

		byte[] bytes = this.jsonMapper.writeValueAsBytes(request);

		EnclaveServiceRequest<MyBean> readRequest = this.jsonMapper.readValue(bytes, new TypeReference<EnclaveServiceRequest<MyBean>>() {
		});

		Assertions.assertEquals(request.getAction(), readRequest.getAction());
		Assertions.assertEquals(myBean.value, readRequest.getData().value);
	}

	static class MyBean implements Serializable {

		private static final long serialVersionUID = 1L;

		@JsonProperty
		double value = Math.random();
	}

}
