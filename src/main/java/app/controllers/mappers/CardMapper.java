package app.controllers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import app.controllers.beans.CardBean;
import app.model.entities.Card;
import app.model.entities.CardInstance;
import app.services.beans.CardServiceBean;

@Mapper
public interface CardMapper
{
	@Mappings({
		@Mapping(target = "deckId",   source = "c.deck.id"),
		@Mapping(target = "cardId",   source = "c.id"),
		@Mapping(target = "sideBToA", expression = "java(!i.isDisabled())")
	})
	CardBean cardAndInstanceToCardBean(
			Card c,
			CardInstance i);
	
	// FIXME: See MapStruct's future versions.
	// Method implemented in order to mantain model encapsulation.
	default void updateCardServiceBeanFromCardBean(
			CardBean s,
			CardServiceBean t)
	{
		if (s == null || t == null)
		{
			return;
		}
		
		Card card = t.getCard();
		if (card == null)
		{
			return;
		}
		
		CardInstance sideBToA = t.getSideBToA();
		if (sideBToA == null)
		{
			return;
		}
		
		card.setNotes(s.getNotes());
		card.setSideA(s.getSideA());
		card.setSideB(s.getSideB());
		card.setTags(s.getTags());
		
		sideBToA.setDisabled(!s.isSideBToA());
	}
}
