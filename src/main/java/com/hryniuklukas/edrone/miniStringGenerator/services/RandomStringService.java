package com.hryniuklukas.edrone.miniStringGenerator.services;

import com.hryniuklukas.edrone.miniStringGenerator.model.UserRequest;
import com.hryniuklukas.edrone.miniStringGenerator.repos.UserRequestRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
@Service
public class RandomStringService {

  private final Random rand;
  private final UserRequestRepo userRequestRepo;
  Logger logger = LoggerFactory.getLogger(RandomStringService.class);

  @Autowired
  RandomStringService(UserRequestRepo userRequestRepo) {
    this.userRequestRepo = userRequestRepo;
    rand = new Random();
  }
  @Async
  public void startStringGenerationJob(UserRequest request) throws InterruptedException {
    int minLength = request.getMinLength();
    int maxLength = request.getMaxLength();
    String charset = request.getCharSet();
    int numberOfStringsRequested = request.getNumberOfStringsRequested();

    Set<String> output = new HashSet<>();
    int currentToGenerate;
    int alreadyGenerated = 0;
    for (int stringLength = minLength; stringLength <= maxLength; stringLength++) {
      if (stringLength == maxLength) {
        currentToGenerate = numberOfStringsRequested - alreadyGenerated;
      } else {
        currentToGenerate = rand.nextInt(numberOfStringsRequested - alreadyGenerated);
      }
      alreadyGenerated += currentToGenerate;
      output.addAll(getRandomStringsSet(stringLength, charset, currentToGenerate));
      logger.info("Length: {} Number generated: {} ", stringLength, currentToGenerate);
    }
    request.setRandomStringSet(output);
    request.generateDocumentTxt();
    request.setStatusAsFinished();
    Thread.sleep(15000); //For test purposes
    userRequestRepo.save(request);
  }

  private Set<String> getRandomStringsSet(
      int length, String charset, int numbersOfStringsToGenerate) {
    Set<String> output = new HashSet<>();
    int iterationsCounterMetric = 0;
    int charsetLength = charset.length();
    do {
      StringBuilder stringBuilder = new StringBuilder(length);
      for (int i = 0; i < length; i++) {
        int randomizedIndex = rand.nextInt(charsetLength);
        stringBuilder.append(charset.charAt(randomizedIndex));
      }
      output.add(stringBuilder.toString());
      iterationsCounterMetric++;
    } while (output.size() < numbersOfStringsToGenerate);
    logger.info(
        "Iterations required: {}, Strings requested: {}",
        iterationsCounterMetric,
        numbersOfStringsToGenerate);
    return output;
  }


  public int possibleNumberOfStrings(UserRequest request) {
    int possibleNumber = 0;
    int possibleChars = request.getCharSet().length();
    int minLength = request.getMinLength();
    int maxLength = request.getMaxLength();

    for (int i = minLength; i <= maxLength; i++) {
      possibleNumber += possibleChars ^ i;
    }
    return possibleNumber;
  }
}
