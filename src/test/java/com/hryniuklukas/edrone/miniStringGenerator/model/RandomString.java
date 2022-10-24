package com.hryniuklukas.edrone.miniStringGenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "generatedStrings")
public class RandomString {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserRequest request;
    private String content;
}
