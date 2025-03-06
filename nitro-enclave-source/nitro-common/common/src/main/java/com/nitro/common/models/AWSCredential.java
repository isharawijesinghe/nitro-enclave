package com.nitro.common.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AWSCredential {

	@JsonProperty("aws-region")
	private String region;

	@JsonProperty("aws-access-key-id")
	private String accessKeyId;

	@JsonProperty("aws-secret-access-key")
	private String secretAccessKey;

	@JsonProperty("aws-session_token")
	private String sessionToken;

}
