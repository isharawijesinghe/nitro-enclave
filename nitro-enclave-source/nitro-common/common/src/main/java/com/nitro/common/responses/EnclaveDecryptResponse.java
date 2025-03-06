package com.nitro.common.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class EnclaveDecryptResponse {
    private String value;
}
