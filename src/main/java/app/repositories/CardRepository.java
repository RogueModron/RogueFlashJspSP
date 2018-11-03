package app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import app.model.entities.Card;
import app.repositories.projections.CardSearchItem;

public interface CardRepository extends JpaRepository<Card, Integer>
{
	public Optional<Card> findCardByIdAndDeckId(
			Integer cardId,
			Integer deckId);
	
	@Query("select "
			+ "c.id as cardId, "
			+ "c.sideA as sideA, "
			+ "c.sideB as sideB, "
			+ "c.notes as notes, "
			+ "c.tags as tags, "
			+ "i.disabled as sideAToBDisabled "
			+ "from Deck d "
			+ "inner join Card c "
			+ "on d.id = c.deck.id "
			+ "left outer join CardInstance i "
			+ "on c.id = i.card.id "
			+ "where d.id = :deckId "
			+ "and ("
			+ " c.sideA like %:descriptionFilter% "
			+ " or c.sideB like %:descriptionFilter% "
			+ " or c.tags like %:descriptionFilter%) "
			+ "and i.sideAToB = true "
			+ "order by "
			+ "tags, "
			+ "sideA, "
			+ "sideB, "
			+ "cardId "
			)
	@Transactional(readOnly = true)
	List<CardSearchItem> search(
			@Param("deckId") Integer deckId,
			@Param("descriptionFilter") String descriptionFilter,
			/* @Nullable */ Pageable pageable);
}
