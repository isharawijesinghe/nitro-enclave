package com.nitro.common.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nitro.common.models.AWSCredential;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@Jacksonized
@JsonIgnoreProperties(value = {"base64Encoded"})
@AllArgsConstructor
public class EnclaveServiceRequest<T> {
    @JsonProperty("id")
    private String id;

    @JsonProperty("action")
    private String action;

    @JsonProperty("data")
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@modelClass")
    private T data;

    @JsonProperty("aws-credentials")
    private AWSCredential awsCredentials;

    public EnclaveServiceRequest() {
        this.id = UUID.randomUUID().toString();
    }
}
