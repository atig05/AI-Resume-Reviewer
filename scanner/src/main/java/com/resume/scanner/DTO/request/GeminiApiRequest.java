package com.resume.scanner.DTO.request;


import com.resume.scanner.DTO.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiApiRequest {
    private List<Content> contents;
}

