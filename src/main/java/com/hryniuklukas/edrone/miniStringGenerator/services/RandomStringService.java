package com.hryniuklukas.edrone.miniStringGenerator.services;

import com.hryniuklukas.edrone.miniStringGenerator.model.RandomString;
import com.hryniuklukas.edrone.miniStringGenerator.model.UserRequest;
import com.hryniuklukas.edrone.miniStringGenerator.repos.RandomStringRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class RandomStringService {
    private final RandomStringRepo randomStringRepo;

    public List<RandomString> generateStringsBasedOnRequest(UserRequest userRequest){
        char[] charSet = userRequest.getCharSet();
        int minLength = userRequest.getMinLength();
        int maxLength = userRequest.getMaxLength();
        int numberRequested = userRequest.getNumberOfStringsRequested();


        return null;
    }
    public List<String> generateAllStrings(int length, String charset){
        return generateAllPossibleStrings(length, charset.toCharArray(), "", charset.length());
    }

    private List<String> generateAllPossibleStrings(int length, char[] charset, String prefix, int setLength){
        List<String> output = new ArrayList<String>();
        if (length == 0)
        {
            output.add(prefix);
            return output;
        }

        for (int i = 0; i < setLength; ++i)
        {

            String newPrefix = prefix + charset[i];

            output = Stream.concat(
                    output.stream(),
                    generateAllPossibleStrings(length - 1,charset, newPrefix, setLength).stream())
                    .toList();
        }
        return output;
    }

    public int possibleNumberOfStrings(int minLength, int maxLength, String tempCharSet){
        int possibleNumber = 0;
        int possibleChars = tempCharSet.length();
        for(int i = minLength; i<=maxLength; i++){
            possibleNumber+=possibleChars^i;
        }
        return possibleNumber;
    }
}
