package app.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import app.controllers.beans.DeckBean;
import app.controllers.mappers.DeckMapper;
import app.model.entities.Deck;
import app.repositories.DeckRepository;

@Controller
@RequestMapping("/deck")
public class DeckController
{
	@Autowired
	private DeckRepository deckRepository;
	
	@Autowired
	private DeckMapper deckMapper;
	
	
	@GetMapping(path = {
		"/",
		"/{id}"
	})
	public String getDeck(
			@PathVariable(required = false) Integer id,
			Model model)
	{
		Deck deck = null;
		
		if (id != null && id != 0)
		{
			Optional<Deck> deckOpt = deckRepository.findById(id);
			if (deckOpt.isPresent())
			{
				deck = deckOpt.get();
			}
			else
			{
				return "redirect:/decks";
			}
		}
		
		if (deck == null)
		{
			deck = new Deck("");
			deck = deckRepository.save(deck);
		}
		
		DeckBean deckBean = deckMapper.deckToDeckBean(deck);
		
		model.addAttribute("PageBean", deckBean);
		return "deck";
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.OK)
	public void saveDeck(@ModelAttribute DeckBean deckBean)
	{
		if (deckBean.getDeckId() == 0)
		{
			return;
		}
		
		Optional<Deck> deckOpt = deckRepository.findById(deckBean.getDeckId());
		if (!deckOpt.isPresent())
		{
			return;
		}
		
		Deck deck = deckOpt.get();
		deckMapper.updateDeckFromDeckBean(
				deckBean,
				deck);
		
		deckRepository.save(deck);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteDeck(@PathVariable Integer id)
	{
		if (id != null && id != 0)
		{
			deckRepository.deleteById(id);
		}
	}
}
