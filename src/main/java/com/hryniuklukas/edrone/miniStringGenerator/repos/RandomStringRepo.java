package com.hryniuklukas.edrone.miniStringGenerator.repos;

import com.hryniuklukas.edrone.miniStringGenerator.model.RandomString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomStringRepo extends JpaRepository<RandomString, Long> {
}
