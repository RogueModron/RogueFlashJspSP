package app.repositories;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import app.TestTransactionManagerConfig;

@SpringJUnitConfig(TestTransactionManagerConfig.class)
@ActiveProfiles("default")
public class TestCardRepository
{
	@Autowired
	private CardRepository cardRepository;
	
	
	@Test
	public void testFindCardByIdAndDeckId()
	{
		assertDoesNotThrow(
				() -> {
					cardRepository.findCardByIdAndDeckId(11, 1);
				});
	}
	
	@Test
	public void testSearch()
	{
		assertDoesNotThrow(
				() -> {
					Pageable p = PageRequest.of(1, 10);
					cardRepository.search(1, "test", p);
				});
	}
}
