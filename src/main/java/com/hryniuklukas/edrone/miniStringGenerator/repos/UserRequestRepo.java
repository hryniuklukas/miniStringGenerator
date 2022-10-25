package com.hryniuklukas.edrone.miniStringGenerator.repos;

import com.hryniuklukas.edrone.miniStringGenerator.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRequestRepo extends JpaRepository<UserRequest, Long> {
}
