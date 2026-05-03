package com.resume.scanner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resume.scanner.DTO.response.GoogleAuthTokenResp;
import com.resume.scanner.apiclient.WebClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;


import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleAuthService {

    @Value("${google.auth.url}")
    private String googleTokenUrl;

    @Value("${google.userInfo.url}")
    private String googleUserInfoUrl;

    @Value("${google.auth.clientID}")
    private String googleClientID;

    @Value("${google.auth.clientSecret}")
    private String googleClientSecret;

    @Autowired
    private WebClientConfig webClientConfig;

    ObjectMapper objectMapper = new ObjectMapper();

    public String googleAuth(String code){
        Map<String, String> formData = new HashMap<>();
        formData.put("code", code);
        formData.put("client_id", googleClientID);
        formData.put("client_secret", googleClientSecret);
        formData.put("redirect_uri", "https://ai-resume-scanner-frontend-iyou.onrender.com");
        formData.put("grant_type", "authorization_code"); //
        //System.out.println(formData);
        ResponseEntity<String> tokenResponse = null;
        GoogleAuthTokenResp googleAuthTokenResp = null;
        try{
            tokenResponse =
                    webClientConfig.callPostForm(googleTokenUrl, formData, String.class);
            System.out.println(tokenResponse +
                    "ddd");
            googleAuthTokenResp = objectMapper.readValue(tokenResponse.getBody(), GoogleAuthTokenResp.class);
        }catch (WebClientResponseException e){
            System.out.println(e.toString()+" "+e.getMessage()+" "+e.getCause()+" "+e.getResponseBodyAsString()+" ");
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Token Response: " + googleAuthTokenResp);

        ResponseEntity<Map> tokenResp = webClientConfig.callGetApi(googleUserInfoUrl+"?id_token="+googleAuthTokenResp.getIdToken(), new ParameterizedTypeReference<Map>() {
        });
        System.out.println(tokenResp);
        System.out.println(tokenResp.getBody().get("email"));
        //ResponseEntity<Map> userInfoResp = webClientConfig.callGetApi(googleUserInfoUrl+"?id_token="+googleAuthTokenResp.getAccessToken(),Map.class);
        return tokenResp.getBody().get("email").toString();
    }
}
