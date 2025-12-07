package com.resume.scanner.controller;



import com.resume.scanner.DTO.request.Questions;
import com.resume.scanner.service.GeminiChatService;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/ai/chat")
@CrossOrigin(origins = "*")
public class GeminiChatController {

    @Autowired
    private GeminiChatService geminiChatService;

    @PostMapping("question")
    public ResponseEntity<String> askGemini(@RequestBody Map<String, String> payload){
        String question = payload.get("QUESTION");
        String text = "";
//        try {
//            // 1. Decode Base64
//            byte[] bytes = Base64.getDecoder().decode(question);
//
//            // 2. Read DOCX using Apache POI
//            XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(bytes));
//            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
//
//            // 3. Get text content
//            text = extractor.getText();
//
//            extractor.close();
//            doc.close();
//            System.out.println(text);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error reading docx: " + e.getMessage());
//        }
        String answer = geminiChatService.getAnswer(question);
        return ResponseEntity.ok(answer);
    }

    @PostMapping("payload")
    public ResponseEntity<String> askGeminiQuestions(@RequestBody Questions payload){

        String answer = geminiChatService.getAnswer(payload.getPayload());
        return ResponseEntity.ok(answer);
    }
}
