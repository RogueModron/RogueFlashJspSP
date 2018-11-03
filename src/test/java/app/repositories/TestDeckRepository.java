package app.repositories;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import app.TestTransactionManagerConfig;

@SpringJUnitConfig(TestTransactionManagerConfig.class)
@ActiveProfiles("default")
public class TestDeckRepository
{
	@Autowired
	private DeckRepository deckRepository;
	
	
	@Test
	public void testSearch()
	{
		assertDoesNotThrow(
				() -> {
					/*
					Sort s = Sort.by(
					Sort.Order.asc("description"),
					Sort.Order.asc("numberOfSides"));
					 */
					Pageable p = PageRequest.of(1, 10);
					deckRepository.search("test", p);
				});
		
		assertThrows(
				Exception.class,
				() -> {
					deckRepository.search("test", null);
				});
	}
}
