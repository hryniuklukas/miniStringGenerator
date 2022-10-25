package com.hryniuklukas.edrone.miniStringGenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
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
    @OneToMany(
            mappedBy = "request",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<RandomString> stringSet = new HashSet<>();

    private String charSet;
    private int minLength;
    private int maxLength;
    private int numberOfStringsRequested;

    public void setRandomStringSet(Set<String> randomStrings){
        randomStrings.forEach(string -> stringSet.add(new RandomString(this, string)));
    }


}
