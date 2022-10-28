package com.hryniuklukas.edrone.miniStringGenerator;

import com.hryniuklukas.edrone.miniStringGenerator.exceptions.ImpossibleAmountOfStringsRequestedException;
import com.hryniuklukas.edrone.miniStringGenerator.model.UserRequest;
import com.hryniuklukas.edrone.miniStringGenerator.repos.UserRequestRepo;
import com.hryniuklukas.edrone.miniStringGenerator.services.JobDelegationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class MiniStringGeneratorApplicationTests {
	@Autowired
	JobDelegationService jobDelegationService;
	@Autowired
	UserRequestRepo userRequestRepo;
	@Test
	void assertThatJobsAreEmpty() {
		final int number = jobDelegationService.getNumberOfJobsRunning();
		assertEquals(0,number);
	}
	@Test
	void assertThatImpossibleRequestThrowsException(){
		UserRequest req = new UserRequest(1,1,5,"ab" );
		userRequestRepo.save(req);
		assertThrows(ImpossibleAmountOfStringsRequestedException.class, ()->jobDelegationService.orchestrateJob(req));
	}
}
