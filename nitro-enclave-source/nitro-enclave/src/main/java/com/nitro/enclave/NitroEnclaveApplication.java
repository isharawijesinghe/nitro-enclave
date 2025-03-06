package com.nitro.enclave;

import com.nitro.enclave.autoconfigure.EnableNitroEnclavesEnclaveSide;
import com.nitro.enclave.enclave.server.NitroEnclaveServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableNitroEnclavesEnclaveSide
public class NitroEnclaveApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(NitroEnclaveApplication.class, args);
		NitroEnclaveServer server = ctx.getBean(NitroEnclaveServer.class);
		server.run();
	}

}
