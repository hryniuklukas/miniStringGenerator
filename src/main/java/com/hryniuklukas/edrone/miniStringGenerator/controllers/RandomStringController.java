package com.hryniuklukas.edrone.miniStringGenerator.controllers;

import com.hryniuklukas.edrone.miniStringGenerator.model.UserRequest;
import com.hryniuklukas.edrone.miniStringGenerator.services.JobDelegationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/strings")
public class RandomStringController {
  private final JobDelegationService jobDelegationService;

  @PostMapping
  public String generateGivenAmountOfStrings(@RequestBody UserRequest request){
    return "Your request has been accepted, ID: " + jobDelegationService.orchestrateJob(request);
  }
  @GetMapping("/{id}")
  public String getResultOfRequest(@PathVariable Long id){
    return jobDelegationService.getJobResultForGivenRequest(id);
  }
  @GetMapping("/running")
  public List<String> getAllCurrentlyRunning(){
    return jobDelegationService.getAllRunningJobs();
  }
}
