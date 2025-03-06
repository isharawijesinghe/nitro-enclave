package com.nitro.host;

import com.nitro.enclave.autoconfigure.EnableNitroEnclavesHostSide;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableNitroEnclavesHostSide
public class NitroHostApplication {

	public static void main(String[] args) {
		SpringApplication.run(NitroHostApplication.class, args);
	}

}
