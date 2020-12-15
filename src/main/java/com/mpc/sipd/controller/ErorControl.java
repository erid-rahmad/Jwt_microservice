package com.mpc.sipd.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ErorControl  {

    @PostMapping(value = "/invalid-token")
    public ResponseEntity<?> handleInvalidToken(){
        Map<String, Object> response = new HashMap<>();

        return ResponseEntity.ok(response);
    }
}
