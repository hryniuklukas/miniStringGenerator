package com.hryniuklukas.edrone.miniStringGenerator.services;

import com.hryniuklukas.edrone.miniStringGenerator.exceptions.ImpossibleAmountOfStringsRequestedException;
import com.hryniuklukas.edrone.miniStringGenerator.model.JobStatus;
import com.hryniuklukas.edrone.miniStringGenerator.model.UserRequest;
import com.hryniuklukas.edrone.miniStringGenerator.repos.UserRequestRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JobDelegationService {
  private final UserRequestRepo userRequestRepo;
  private final RandomStringService randomStringService;

  public String orchestrateJob(UserRequest request)
      throws ImpossibleAmountOfStringsRequestedException {
    System.out.println("Possible: "+ randomStringService.possibleNumberOfStrings(request));
    System.out.println("Requested: "+ request.getNumberOfStringsRequested());
    System.out.println("CharsetLength: "+request.getCharSet().length());
    System.out.println("MinLength: "+request.getMinLength());
    System.out.println("MaxLength: "+request.getMaxLength());

    if (randomStringService.possibleNumberOfStrings(request)
        >= request.getNumberOfStringsRequested()) {
      request.setStatusAsRunning();
      userRequestRepo.save(request);
      randomStringService.startStringGenerationJob(request);
      return "Job has been started and assigned ID: " + request.getId();
    } else {
      throw new ImpossibleAmountOfStringsRequestedException(
          "Impossible to generate given amount for given constraints.");
    }
  }

  public String getJobResultForGivenRequest(Long id) {
    Optional<UserRequest> queriedRequest = userRequestRepo.findById(id);
    if (queriedRequest.isPresent()) {
      if (queriedRequest.get().getJobStatus().equals(JobStatus.FINISHED)) {
        return queriedRequest.get().getDocumentTxt();
      } else {
        return "Job not yet finished";
      }
    } else {
      return "Request with given ID doesn't exist";
    }
  }

  @Transactional
  public List<String> getAllRunningJobs() {
    List<String> jobsList = new ArrayList<>();
    Optional<List<UserRequest>> queriedListOfRequests =
        userRequestRepo.findAllByJobStatus(JobStatus.RUNNING);
    queriedListOfRequests.ifPresent(
        userRequests ->
            userRequests.forEach(userRequest -> jobsList.add(userRequest.getId().toString())));
    return jobsList;
  }

  @Transactional
  public int getNumberOfJobsRunning() {
    return userRequestRepo.findAllByJobStatus(JobStatus.RUNNING).map(List::size).orElse(0);
  }
}
