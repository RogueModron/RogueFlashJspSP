package app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.model.entities.Card;
import app.model.entities.CardInstance;
import app.model.entities.Deck;
import app.repositories.CardInstanceRepository;
import app.repositories.CardRepository;
import app.repositories.DeckRepository;
import app.services.beans.CardServiceBean;

@Service
@Transactional
public class CardService
{
	@Autowired
	private DeckRepository deckRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private CardInstanceRepository cardInstanceRepository;
	
	
	public CardServiceBean create(Integer deckId)
	{
		Optional<Deck> deckOpt = deckRepository.findById(deckId);
		if (!deckOpt.isPresent())
		{
			throw new IllegalArgumentException();
		}
		
		Card card = new Card(
				deckOpt.get(),
				"",
				"");
		card = cardRepository.save(card);
		
		CardInstance sideAToB = new CardInstance(card);
		sideAToB.setSideAToB(true);
		CardInstance sideBToA = new CardInstance(card);
		sideAToB = cardInstanceRepository.save(sideAToB);
		sideBToA = cardInstanceRepository.save(sideBToA);
		
		CardServiceBean result = new CardServiceBean(
				card,
				sideAToB,
				sideBToA);
		return result;
	}
	
	public void delete(Integer cardId)
	{
		cardRepository.deleteById(cardId);
	}
	
	public Optional<CardServiceBean> find(
			Integer cardId,
			Integer deckId) throws Exception
	{
		Optional<Card> cardOpt = cardRepository.findCardByIdAndDeckId(
				cardId,
				deckId);
		if (!cardOpt.isPresent())
		{
			return Optional.empty();
		}
		
		List<CardInstance> cardInstances = cardInstanceRepository.findByCardId(cardId);
		if (cardInstances.size() != 2)
		{
			throw new Exception("Invalid card.");
		}
		
		Optional<CardInstance> sideAToBOpt = cardInstances.
				stream().
				filter(ci -> ci.isSideAToB()).
				findFirst();
		Optional<CardInstance> sideBToAOpt = cardInstances.
				stream().
				filter(ci -> !ci.isSideAToB()).
				findFirst();
		if (!sideAToBOpt.isPresent() || !sideBToAOpt.isPresent())
		{
			throw new Exception("Invalid card.");
		}
		
		CardServiceBean result = new CardServiceBean(
				cardOpt.get(),
				sideAToBOpt.get(),
				sideBToAOpt.get());
		return Optional.of(result);
	}
	
	public CardServiceBean save(CardServiceBean cardServiceBean)
	{
		if (cardServiceBean.getCard() == null ||
				cardServiceBean.getSideAToB() == null ||
				cardServiceBean.getSideBToA() == null)
		{
			throw new IllegalArgumentException();
		}
		if (!cardServiceBean.getCard().equals(cardServiceBean.getSideAToB().getCard()) ||
				!cardServiceBean.getCard().equals(cardServiceBean.getSideBToA().getCard()))
		{
			throw new IllegalArgumentException();
		}
		if (cardServiceBean.getSideAToB().isSideAToB() == cardServiceBean.getSideBToA().isSideAToB())
		{
			throw new IllegalArgumentException();
		}
		
		Card card = cardRepository.save(cardServiceBean.getCard());
		
		CardInstance sideAToB = cardInstanceRepository.save(cardServiceBean.getSideAToB());
		CardInstance sideBToA = cardInstanceRepository.save(cardServiceBean.getSideBToA());
		
		CardServiceBean result = new CardServiceBean(
				card,
				sideAToB,
				sideBToA);
		return result;
	}
}
