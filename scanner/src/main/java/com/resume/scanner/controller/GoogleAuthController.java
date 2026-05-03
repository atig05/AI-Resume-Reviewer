package com.resume.scanner.controller;

import com.resume.scanner.service.GoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GoogleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService;
    @GetMapping("/sign-in")
    public ResponseEntity<?> handleGoogleCallBack(@RequestParam String code){
        System.out.println("signin");
        return ResponseEntity.ok(googleAuthService.googleAuth(code));
    }
}
