package com.resume.scanner.DTO.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GeminiApiResponse {

    @JsonProperty("candidates")
    private List<Candidate> candidates;

    @JsonProperty("usageMetadata")
    private UsageMetadata usageMetadata;
    private String modelVersion;
    private String responseId;

    @Data
    public static class Candidate {
        private CandidateContent content;
        private String finishReason;
        private int index;
    }

    @Data
    public static class CandidateContent {
        private List<ContentPart> parts;
        private String role;
    }

    @Data
    public static class ContentPart {
        private String text;
    }

    @Data
    public static class UsageMetadata {
        private int promptTokenCount;
        private int candidatesTokenCount;
        private int totalTokenCount;
        private List<PromptTokensDetail> promptTokensDetails;
        private int thoughtsTokenCount;
    }

    @Data
    public static class PromptTokensDetail {
        private String modality;
        private int tokenCount;
    }
}


