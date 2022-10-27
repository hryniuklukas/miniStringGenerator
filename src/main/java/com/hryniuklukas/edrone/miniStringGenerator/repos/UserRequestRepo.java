package com.hryniuklukas.edrone.miniStringGenerator.repos;

import com.hryniuklukas.edrone.miniStringGenerator.model.JobStatus;
import com.hryniuklukas.edrone.miniStringGenerator.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRequestRepo extends JpaRepository<UserRequest, Long> {
    Optional<List<UserRequest>> findAllByJobStatus(JobStatus status);
}
