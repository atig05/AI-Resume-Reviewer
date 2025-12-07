package com.resume.scanner.apiclient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebClientConfig {

    private WebClient webClient = WebClient.create();

    public <T, R> ResponseEntity<R> callGetApi(String path, ParameterizedTypeReference<R> responseType){
        return webClient.get().uri(path)
                .headers(httpHeaders -> buildHeaders().forEach(httpHeaders::set))
                .retrieve()
                .toEntity(responseType)
                .block();
    }

    public <T, R> ResponseEntity<R> callPostApi(String path, T requestBody, Class<R> responseType, Map<String ,String> apiHeaders) {
        return webClient.post()
                .uri(path)
                .headers(httpHeaders -> apiHeaders.forEach(httpHeaders::set))
                .bodyValue(requestBody)
                .retrieve()
                .toEntity(responseType) // Retrieves full ResponseEntity<R>
                .block();
    }

    private Map<String ,String> buildHeaders(){
        Map<String ,String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
