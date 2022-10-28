package com.hryniuklukas.edrone.miniStringGenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userRequests")
public class UserRequest {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Transient private Set<String> stringSet = new HashSet<>();

  @Lob private String documentTxt = "";

  @Transient private StringBuilder builder = new StringBuilder();
  @Enumerated(EnumType.STRING)
  private JobStatus jobStatus;

  private String charSet;
  private int minLength;
  private int maxLength;
  private int numberOfStringsRequested;

  public UserRequest(int minLength, int maxLength, int numberOfStringsRequested, String charSet){
    this.minLength=minLength;
    this.maxLength=maxLength;
    this.numberOfStringsRequested=numberOfStringsRequested;
    this.charSet=charSet;
  }
  public void setStatusAsRunning() {
    this.jobStatus = JobStatus.RUNNING;
  }

  public void setStatusAsFinished() {
    this.jobStatus = JobStatus.FINISHED;
  }

  public void setRandomStringSet(Set<String> randomStrings) {
    this.stringSet = randomStrings;
  }

  public void generateDocumentTxt() {
    stringSet.forEach(this::concatStringToTxt);
    this.documentTxt = builder.toString();
  }

  public void concatStringToTxt(String s) {
    builder.append(s).append("\n");
  }
}
