package app.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import app.controllers.beans.ReviewBean;
import app.controllers.mappers.ReviewMapper;
import app.controllers.propertyEditors.ReviewValuesPropertyEditor;
import app.model.entities.CardInstance;
import app.model.planner.ReviewValues;
import app.services.ReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController
{
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ReviewMapper reviewMapper;
	
	
	@GetMapping("/{deckId}")
	public String getReview(
			@PathVariable Integer deckId,
			Model model)
	{
		Integer n = reviewService.countCardInstancesToReview(deckId);
		if (n > 0)
		{
			model.addAttribute("deckId", deckId);
			return "review";
		}
		
		return "redirect:/deck/" + deckId;
	}
	
	@GetMapping("/next/{deckId}")
	public ResponseEntity<ReviewBean> getCardInstance(@PathVariable Integer deckId)
	{
		if (deckId == null || deckId == 0)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		Optional<CardInstance> cardInstanceOpt = reviewService.nextCardInstanceToReview(deckId);
		if (!cardInstanceOpt.isPresent())
		{
			return ResponseEntity.ok(null);
		}
		
		CardInstance cardInstance = cardInstanceOpt.get();
		
		ReviewBean reviewBean = reviewMapper.cardAndInstanceToReviewBean(
				cardInstance.getCard(),
				cardInstance);
		
		return ResponseEntity.ok(reviewBean);
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.OK)
	public void reviewCardInstance(
			@RequestParam("cardInstanceId") Integer cardInstanceId,
			@RequestParam("value") ReviewValues value)
	{
		reviewService.review(
				cardInstanceId,
				value);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder)
	{
		dataBinder.registerCustomEditor(
				ReviewValues.class,
				new ReviewValuesPropertyEditor());
	}
}
