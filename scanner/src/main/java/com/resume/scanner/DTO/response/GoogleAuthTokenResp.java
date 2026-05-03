package com.resume.scanner.DTO.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoogleAuthTokenResp {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Integer expires;

    @JsonProperty("refresh_token")
    private String refeshToken;

    private String scope;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("id_token")
    private String idToken;

}
