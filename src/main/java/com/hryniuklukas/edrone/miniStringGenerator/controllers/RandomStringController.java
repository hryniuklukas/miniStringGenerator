package com.hryniuklukas.edrone.miniStringGenerator.controllers;

import com.hryniuklukas.edrone.miniStringGenerator.model.TempRequest;
import com.hryniuklukas.edrone.miniStringGenerator.services.RandomStringService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/strings/")
public class RandomStringController {
    private final RandomStringService randomStringService;

    @GetMapping
    List<String> generateAllStrings(@RequestBody TempRequest request){
        return randomStringService.generateAllStrings(request.getLength(), request.getCharset());
    }
}
