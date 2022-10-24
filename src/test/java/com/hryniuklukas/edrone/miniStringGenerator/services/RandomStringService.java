package com.hryniuklukas.edrone.miniStringGenerator.services;

import com.hryniuklukas.edrone.miniStringGenerator.model.RandomString;
import com.hryniuklukas.edrone.miniStringGenerator.model.UserRequest;
import com.hryniuklukas.edrone.miniStringGenerator.repos.RandomStringRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RandomStringService {
    private final RandomStringRepo randomStringRepo;

    public List<RandomString> generateStringsBasedOnRequest(UserRequest userRequest){
        String tempCharSet = userRequest.getCharSet();
        int minLength = userRequest.getMinLength();
        int maxLength = userRequest.getMaxLength();
        int numberRequested = userRequest.getNumberOfStringsRequested();


        return null;
    }
}
