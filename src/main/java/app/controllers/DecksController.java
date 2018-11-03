package app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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
import app.repositories.DeckRepository;
import app.repositories.projections.DeckSearchItem;

@Controller
@RequestMapping("/decks")
public class DecksController
{
	@Autowired
	private DeckRepository deckRepository;
	
	
	@GetMapping
	public String getDecks()
	{
		return "decks";
	}
	
	@GetMapping("/search")
	@ResponseBody
	public List<DeckSearchItem> searchDecks(@ModelAttribute FilterBean filterBean)
	{
		List<DeckSearchItem> result = deckRepository.search(
				filterBean.getFilterText(),
				filterBean.getPageable());
		return result;
	}
	
	@DeleteMapping("/{ids}")
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteDecks(@PathVariable Integer[] ids)
	{
		for (Integer id : ids)
		{
			deckRepository.deleteById(id);
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
