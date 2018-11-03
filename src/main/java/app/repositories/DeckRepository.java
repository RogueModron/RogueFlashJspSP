package app.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import app.model.entities.Deck;
import app.repositories.projections.DeckSearchItem;

public interface DeckRepository extends JpaRepository<Deck, Integer>
{
	@Query("select "
			+ "d.id as deckId, "
			+ "min(d.description) as description, "
			+ "min(d.notes) as notes, "
			+ "count(i) as numberOfSides "
			+ "from Deck d "
			+ "left join Card c "
			+ "on d.id = c.deck.id "
			+ "left join CardInstance i "
			+ "on c.id = i.card.id "
			+ "where (i.disabled is null or i.disabled = false) "
			+ "and d.description like %:descriptionFilter% "
			+ "group by "
			+ "d.id "
			+ "order by "
			+ "description, "
			+ "numberOfSides "
			)
	@Transactional(readOnly = true)
	List<DeckSearchItem> search(
			@Param("descriptionFilter") String descriptionFilter,
			/* @Nullable */ Pageable pageable);
}
