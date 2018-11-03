package app.controllers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import app.controllers.beans.ReviewBean;
import app.model.entities.Card;
import app.model.entities.CardInstance;

@Mapper
public interface ReviewMapper
{
	@Mappings({
		@Mapping(target = "cardId",         source = "c.id"),
		@Mapping(target = "cardInstanceId", source = "i.id")
	})
	ReviewBean cardAndInstanceToReviewBean(
			Card c,
			CardInstance i);
}
