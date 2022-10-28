package com.hryniuklukas.edrone.miniStringGenerator.controllers;

import com.hryniuklukas.edrone.miniStringGenerator.exceptions.ImpossibleAmountOfStringsRequestedException;
import com.hryniuklukas.edrone.miniStringGenerator.model.UserRequest;
import com.hryniuklukas.edrone.miniStringGenerator.services.JobDelegationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/strings")
public class RandomStringController {
  private final JobDelegationService jobDelegationService;

  @PostMapping
  public String generateGivenAmountOfStrings(@RequestBody UserRequest request) {
    try {
      return jobDelegationService.orchestrateJob(request);
    } catch (ImpossibleAmountOfStringsRequestedException e) {
      return "Too many strings requested for given constraints";
    }
  }
  @GetMapping(
          value="/{id}/txt",
          produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public @ResponseBody String getResultsAsFile(@PathVariable Long id){
    return jobDelegationService.getJobResultForGivenRequest(id);
  }
  @GetMapping("/running")
  public List<String> getAllCurrentlyRunning() {
    return jobDelegationService.getAllRunningJobs();
  }

  @GetMapping("/running/count")
  public int getNumberOfRunningJobs() {
    return jobDelegationService.getNumberOfJobsRunning();
  }
}
