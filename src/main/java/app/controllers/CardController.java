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

import app.controllers.beans.CardBean;
import app.controllers.mappers.CardMapper;
import app.services.CardService;
import app.services.beans.CardServiceBean;

@Controller
@RequestMapping("/card")
public class CardController
{
	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardMapper cardMapper;
	
	
	@GetMapping(path = {
		"/{deckId}",
		"/{deckId}/{cardId}"
	})
	public String getCard(
			@PathVariable Integer deckId,
			@PathVariable(required = false) Integer cardId,
			Model model) throws Exception
	{
		if (deckId == null || deckId == 0)
		{
			return "redirect:/decks";
		}
		
		CardServiceBean cardServiceBean = null;
		
		if (cardId != null && cardId != 0)
		{
			Optional<CardServiceBean> cardServiceBeanOpt = cardService.find(
					cardId,
					deckId);
			if (cardServiceBeanOpt.isPresent())
			{
				cardServiceBean = cardServiceBeanOpt.get();
			}
			else
			{
				return "redirect:/decks";
			}
		}
		
		if (cardServiceBean == null)
		{
			cardServiceBean = cardService.create(deckId);
		}
		
		CardBean cardBean = cardMapper.cardAndInstanceToCardBean(
				cardServiceBean.getCard(),
				cardServiceBean.getSideBToA());
		
		model.addAttribute("PageBean", cardBean);
		return "card";
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.OK)
	public void saveCard(@ModelAttribute CardBean cardBean) throws Exception
	{
		if (cardBean.getDeckId() == 0 ||
				cardBean.getCardId() == 0)
		{
			return;
		}
		
		Optional<CardServiceBean> cardServiceBeanOpt = cardService.find(
				cardBean.getCardId(),
				cardBean.getDeckId());
		if (!cardServiceBeanOpt.isPresent())
		{
			return;
		}
		
		CardServiceBean cardServiceBean = cardServiceBeanOpt.get();
		cardMapper.updateCardServiceBeanFromCardBean(
				cardBean,
				cardServiceBean);
		
		cardService.save(cardServiceBean);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteCard(@PathVariable Integer id)
	{
		if (id != null && id != 0)
		{
			cardService.delete(id);
		}
	}
}
