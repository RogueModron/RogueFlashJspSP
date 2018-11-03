package app.controllers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import app.controllers.beans.DeckBean;
import app.model.entities.Deck;

@Mapper
public interface DeckMapper
{
	@Mappings({
		@Mapping(target = "deckId", source = "id")
	})
	DeckBean deckToDeckBean(Deck d);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "version", ignore = true)
	})
	void updateDeckFromDeckBean(
			DeckBean s,
			@MappingTarget Deck t);
}
