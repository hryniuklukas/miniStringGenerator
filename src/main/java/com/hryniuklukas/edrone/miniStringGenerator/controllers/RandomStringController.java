package com.hryniuklukas.edrone.miniStringGenerator.controllers;

import com.hryniuklukas.edrone.miniStringGenerator.model.UserRequest;
import com.hryniuklukas.edrone.miniStringGenerator.services.RandomStringService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/strings")
public class RandomStringController {
  private final RandomStringService randomStringService;

  @GetMapping
  public Set<String> generateGivenAmountOfStrings(@RequestBody UserRequest request) {
    return randomStringService.startStringGenerationJob(request);
  }
}
