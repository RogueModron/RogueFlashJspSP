package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import app.repositories.DeckRepository;

@Controller
public class StartController
{
	@Autowired
	DeckRepository deckRepository;
	
	
	@GetMapping("/")
	public String start()
	{
		if (deckRepository.count() == 0)
		{
			return "redirect:/deck";
		}
		else
		{
			return "redirect:/decks";
		}
	}
}
