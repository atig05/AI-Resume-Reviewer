package com.resume.scanner.service;

import com.resume.scanner.DTO.Content;
import com.resume.scanner.DTO.Part;
import com.resume.scanner.DTO.request.GeminiApiRequest;
import com.resume.scanner.DTO.response.GeminiApiResponse;
import com.resume.scanner.apiclient.WebClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiChatService {

    @Value("${gemini.api.url}")
    private String geminiAPIUrl;

    @Value("${gemini.api.key}")
    private String geminiAPIKey;

    @Autowired
    private WebClientConfig webClientConfig;

    public String getAnswer(String question) {
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[] {
                                Map.of("text", question)
                        } )
                }
        );
        Map<String ,String> headers = new HashMap<>();
        headers.put("x-goog-api-key", geminiAPIKey);
        ResponseEntity<GeminiApiResponse> response = webClientConfig.callPostApi(geminiAPIUrl,
                requestBody,
                GeminiApiResponse.class,
                headers);
        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody().getCandidates().get(0).getContent().getParts().get(0).getText();
        }
        return "Some Error";
    }

    public String getAnswer(List<String> questions) {
        List<Content> contents = new ArrayList<Content>();
        List<Part> parts = new ArrayList<Part>();
        questions.stream().forEach(question->{
            parts.add(new Part(question));
        });
        contents.add(new Content(parts));
        GeminiApiRequest geminiApiRequest = new GeminiApiRequest(contents);
        Map<String ,String> headers = new HashMap<>();
        headers.put("x-goog-api-key", geminiAPIKey);
        //System.out.println(geminiApiRequest);
        ResponseEntity<GeminiApiResponse> response = webClientConfig.callPostApi(geminiAPIUrl,
                geminiApiRequest,
                GeminiApiResponse.class,
                headers);
        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody().getCandidates().get(0).getContent().getParts().get(0).getText();
        }
        return "Some Error";
    }
}
