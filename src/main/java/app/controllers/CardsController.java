package app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import app.controllers.beans.FilterBean;
import app.controllers.propertyEditors.IdArrayPropertyEditor;
import app.repositories.CardRepository;
import app.repositories.projections.CardSearchItem;

@Controller
@RequestMapping("/cards")
public class CardsController
{
	@Autowired
	private CardRepository cardRepository;
	
	
	@GetMapping("/{deckId}")
	public String getCards(
			@PathVariable Integer deckId,
			Model model)
	{
		model.addAttribute("deckId", deckId);
		return "cards";
	}
	
	@GetMapping("/search/{deckId}")
	@ResponseBody
	public List<CardSearchItem> searchCards(
			@PathVariable Integer deckId,
			@ModelAttribute FilterBean filterBean)
	{
		List<CardSearchItem> result = cardRepository.search(
				deckId,
				filterBean.getFilterText(),
				filterBean.getPageable());
		return result;
	}
	
	@DeleteMapping("/{ids}")
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteCards(@PathVariable Integer[] ids)
	{
		for (Integer id : ids)
		{
			cardRepository.deleteById(id);
		}
	}
	
	@InitBinder("ids")
	public void idsBinder(WebDataBinder binder)
	{
		binder.registerCustomEditor(
				Integer[].class,
				new IdArrayPropertyEditor());
	}
}
