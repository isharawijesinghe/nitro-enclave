package com.nitro.common.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Builder
@Getter
@Setter
@ToString
@Jacksonized
@JsonIgnoreProperties(value = {"base64Encoded"})
@NoArgsConstructor
@AllArgsConstructor
public class EnclaveServiceResponse<T> {
    @JsonProperty("action")
    private String action;

    @JsonProperty("data")
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@modelClass")
    private T data;

    @JsonProperty("start-date")
    private Instant startDate;

    @JsonProperty("duration")
    private Long duration;

    @JsonProperty("isError")
    private Boolean isError = false;

    @JsonProperty("error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    @JsonProperty("errorStacktrace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorStacktrace;
}
