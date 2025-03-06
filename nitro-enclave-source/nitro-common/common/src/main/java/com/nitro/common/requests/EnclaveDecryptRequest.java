package com.nitro.common.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nitro.common.models.AWSCredential;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Setter
@ToString
@Jacksonized
@JsonIgnoreProperties(value = {"base64Encoded"})
@AllArgsConstructor
@NoArgsConstructor
public class EnclaveDecryptRequest {
    private String value;
    @JsonProperty("aws-credentials")
    private AWSCredential awsCredentials;
}
