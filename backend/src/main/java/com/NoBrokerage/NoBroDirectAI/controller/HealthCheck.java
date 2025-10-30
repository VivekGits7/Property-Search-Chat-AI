package com.NoBrokerage.NoBroDirectAI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health") // http://localhost:8083/health
public class HealthCheck {
    @GetMapping
    public String healthCheck() {
        return "NoBroDirectAI is Up and Running!";
    }
}
