package com.hryniuklukas.edrone.miniStringGenerator.services;

import com.hryniuklukas.edrone.miniStringGenerator.model.UserRequest;
import com.hryniuklukas.edrone.miniStringGenerator.repos.RandomStringRepo;
import com.hryniuklukas.edrone.miniStringGenerator.repos.UserRequestRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class RandomStringService {
  private final RandomStringRepo randomStringRepo;
  private final Random rand;
  private final UserRequestRepo userRequestRepo;
  Logger logger = LoggerFactory.getLogger(RandomStringService.class);
  private int iterationsCounterMetric;

  @Autowired
  RandomStringService(RandomStringRepo randomStringRepo, UserRequestRepo userRequestRepo) {
    this.randomStringRepo = randomStringRepo;
    this.userRequestRepo = userRequestRepo;
    rand = new Random();
  }

  //  public List<String> generateAllStrings(int length, String charset) {
  //    return generateAllPossibleStrings(length, charset.toCharArray(), "", charset.length());
  //  }
  //
  //  private List<String> generateAllPossibleStrings(
  //      int length, char[] charset, String prefix, int setLength) {
  //    List<String> output = new ArrayList<>();
  //    if (length == 0) {
  //      output.add(prefix);
  //      return output;
  //    }
  //
  //    for (int i = 0; i < setLength; ++i) {
  //
  //      String newPrefix = prefix + charset[i];
  //
  //      output =
  //          Stream.concat(
  //                  output.stream(),
  //                  generateAllPossibleStrings(length - 1, charset, newPrefix,
  // setLength).stream())
  //              .toList();
  //    }
  //    return output;
  //  }
  public CompletableFuture<Set<String>> orchestrateJob(UserRequest request) {
    try {
      return startStringGenerationJob(request);
    } catch (Exception e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  @Async
  public CompletableFuture<Set<String>> startStringGenerationJob(UserRequest request) {
    int minLength = request.getMinLength();
    int maxLength = request.getMaxLength();
    String charset = request.getCharSet();
    int amount = request.getNumberOfStringsRequested();

    iterationsCounterMetric = 0;
    Set<String> output = new HashSet<>();
    int currentToGenerate;
    int alreadyGenerated = 0;
    for (int stringLength = minLength; stringLength <= maxLength; stringLength++) {
      if (stringLength == maxLength) {
        currentToGenerate = amount - alreadyGenerated;
      } else {
        currentToGenerate = rand.nextInt(amount - alreadyGenerated);
      }
      alreadyGenerated += currentToGenerate;
      output.addAll(getRandomStringsSet(stringLength, charset, currentToGenerate));
      logger.info("Length: {} Number generated: {} ",stringLength, currentToGenerate);
    }
    logger.info("Iterations required: {}, Strings requested: {}", iterationsCounterMetric, amount);
    request.setRandomStringSet(output);
    userRequestRepo.save(request);
    return CompletableFuture.completedFuture(output);
  }

  private Set<String> getRandomStringsSet(int length, String charset, int amount) {
    Set<String> output = new HashSet<>();
    int charsetLength = charset.length();
    do {
      StringBuilder stringBuilder = new StringBuilder(length);
      for (int i = 0; i < length; i++) {
        int randomizedIndex = rand.nextInt(charsetLength);
        stringBuilder.append(charset.charAt(randomizedIndex));
      }
      output.add(stringBuilder.toString());
      iterationsCounterMetric++;
    } while (output.size() < amount);
    return output;
  }

  public int possibleNumberOfStrings(int minLength, int maxLength, String tempCharSet) {
    int possibleNumber = 0;
    int possibleChars = tempCharSet.length();
    for (int i = minLength; i <= maxLength; i++) {
      possibleNumber += possibleChars ^ i;
    }
    return possibleNumber;
  }
}
