package com.hryniuklukas.edrone.miniStringGenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userRequests")
public class UserRequest {
    private static @Id @GeneratedValue(strategy = GenerationType.IDENTITY)Long id;
    @OneToMany(
            mappedBy = "request",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RandomString> stringList;

    private String charSet;
    private int minLength;
    private int maxLength;
    private int numberOfStringsRequested;

}
