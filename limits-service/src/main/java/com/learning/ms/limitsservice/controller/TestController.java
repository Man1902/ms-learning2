package com.learning.ms.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/test")
    public void test(){
       // HttpEntity<String> httpEntity = new
        //String response = restTemplate.getForObject("https://httpbin.org/get", String.class);
    }
}
