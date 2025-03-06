package com.nitro.enclave.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.nitro.enclave.configuration.EnclaveConfiguration;
import com.nitro.enclave.configuration.JacksonConfiguration;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({JacksonConfiguration.class, EnclaveConfiguration.class})
public @interface EnableNitroEnclavesEnclaveSide {}
