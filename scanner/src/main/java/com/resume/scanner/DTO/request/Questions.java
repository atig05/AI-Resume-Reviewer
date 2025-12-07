package com.resume.scanner.DTO.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Questions {
    @JsonProperty("QUESTION")
    private List<String> payload;
}
