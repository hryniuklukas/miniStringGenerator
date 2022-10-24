package com.hryniuklukas.edrone.miniStringGenerator.controllers;

import com.hryniuklukas.edrone.miniStringGenerator.services.RandomStringService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/strings/")
public class RandomStringController {
    private final RandomStringService randomStringService;

}
