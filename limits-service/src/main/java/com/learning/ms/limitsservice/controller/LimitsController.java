package com.learning.ms.limitsservice.controller;

import com.learning.ms.limitsservice.config.LimitConfig;
import com.learning.ms.limitsservice.model.Limits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LimitsController {
    @Autowired
    private LimitConfig limitConfig;

    @GetMapping("/limits")
    public Limits retrieveLimits() {
        //return new Limits(1, 1000);
        return new Limits(limitConfig.getMinimum(), limitConfig.getMaximum());
    }
}
