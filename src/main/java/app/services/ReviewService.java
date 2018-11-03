package app.services;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.model.entities.CardInstance;
import app.model.entities.CardPlan;
import app.model.entities.CardReview;
import app.model.planner.Planner;
import app.model.planner.PlannerResult;
import app.model.planner.ReviewValues;
import app.repositories.CardInstanceRepository;
import app.repositories.CardReviewRepository;

@Service
@Transactional
public class ReviewService
{
	@Autowired
	private CardInstanceRepository cardInstanceRepository;
	
	@Autowired
	private CardReviewRepository cardReviewRepository;
	
	
	public Integer countCardInstancesToReview(Integer deckId)
	{
		return cardInstanceRepository.countCardInstancesToReview(
				deckId,
				OffsetDateTime.now());
	}
	
	public Optional<CardInstance> nextCardInstanceToReview(Integer deckId)
	{
		List<CardInstance> cardsInstances = cardInstanceRepository.nextCardInstanceToReview(
				deckId,
				OffsetDateTime.now(),
				PageRequest.of(0, 1));
		if (cardsInstances.size() == 0)
		{
			return Optional.empty();
		}
		else
		{
			return Optional.of(cardsInstances.get(0));
		}
	}
	
	public void review(
			Integer cardInstanceId,
			ReviewValues reviewValue)
	{
		if (reviewValue == null)
		{
			throw new IllegalArgumentException();
		}
		
		Optional<CardInstance> cardInstanceOpt = cardInstanceRepository.findById(cardInstanceId);
		if (!cardInstanceOpt.isPresent())
		{
			throw new IllegalArgumentException();
		}

		CardInstance cardInstance = cardInstanceOpt.get();
		CardPlan cardPlan = cardInstance.getPlan();
		
		OffsetDateTime now = OffsetDateTime.now();
		
		if (cardPlan.getNextDate() != null &&
				cardPlan.getNextDate().isAfter(now))
		{
			throw new IllegalArgumentException();
		}
		
		PlannerResult plannerResult = Planner.planNext(
				reviewValue,
				now,
				cardPlan.getLastDate());
		cardPlan.setLastDate(now);
		cardPlan.setLastDays(plannerResult.getPassedDays());
		cardPlan.setNextDate(plannerResult.getNextDate());
		cardPlan.setNextDays(plannerResult.getDaysNext());
		
		CardReview cardReview = new CardReview(cardInstance);
		cardReview.setDateTime(now);
		cardReview.setValue(reviewValue);
		
		cardInstanceRepository.save(cardInstance);
		cardReviewRepository.save(cardReview);
 	}
}
