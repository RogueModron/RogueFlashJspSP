package app.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import app.TestWebConfig;
import app.model.entities.Deck;
import app.repositories.DeckRepository;

@SpringJUnitWebConfig(TestWebConfig.class)
@ActiveProfiles("default")
public class TestDeckController
{
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private DeckRepository deckRepository;
	
	private MockMvc mockMvc = null;
	
	
	@BeforeEach
	public void setup()
	{
		mockMvc = MockMvcBuilders
				.webAppContextSetup(this.wac)
				.build();
	}
	
	
	@Test
	public void testNewDeck() throws Exception
	{
		long decksBefore = deckRepository.count();
		
		mockMvc
				.perform(MockMvcRequestBuilders.get("/deck/"))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
		long decksAfter = deckRepository.count();
		
		assertTrue(decksAfter > decksBefore);
	}

	@Test
	public void testOpenDeck() throws Exception
	{
		Deck deck = new Deck("");
		deck = deckRepository.save(deck);
		
		mockMvc
				.perform(MockMvcRequestBuilders.get("/deck/{id}", deck.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testSaveDeck() throws Exception
	{
		final String text = "Test!";
		
		Deck deck = new Deck("");
		deck = deckRepository.save(deck);

		mockMvc
				.perform(MockMvcRequestBuilders.post("/deck")
						.param("deckId", Integer.toString(deck.getId()))
						.param("description", text)
						.param("notes", text))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
		deck = deckRepository.findById(deck.getId()).get();
		
		assertEquals(text, deck.getDescription());
		assertEquals(text, deck.getNotes());
	}
	
	@Test
	public void testDeleteDeck() throws Exception
	{
		Deck deck = new Deck("");
		deck = deckRepository.save(deck);
		
		mockMvc
				.perform(MockMvcRequestBuilders.delete("/deck/{id}", deck.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
		boolean exists = deckRepository.findById(deck.getId()).isPresent();
		
		assertFalse(exists);
	}
}
