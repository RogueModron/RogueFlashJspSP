package app.repositories;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import app.TestTransactionManagerConfig;

@SpringJUnitConfig(TestTransactionManagerConfig.class)
@ActiveProfiles("default")
public class TestCardInstanceRepository
{
	@Autowired
	private CardInstanceRepository cardInstanceRepository;
	
	
	@Test
	public void testCountCardInstancesToReview()
	{
		assertDoesNotThrow(
				() -> {
					cardInstanceRepository.countCardInstancesToReview(1, OffsetDateTime.now());
				});
	}
	
	@Test
	public void testFindByCardId()
	{
		assertDoesNotThrow(
				() -> {
					cardInstanceRepository.findByCardId(1);
				});
	}
	
	@Test
	public void testNextCardInstanceToReview()
	{
		assertDoesNotThrow(
				() -> {
					cardInstanceRepository.nextCardInstanceToReview(1, OffsetDateTime.now(), null);
				});
	}
}
