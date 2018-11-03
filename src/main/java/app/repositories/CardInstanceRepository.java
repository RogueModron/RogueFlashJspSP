package app.repositories;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import app.model.entities.CardInstance;

public interface CardInstanceRepository extends JpaRepository<CardInstance, Integer>
{
	@Query("select count(i) "
			+ "from Deck d "
			+ "left outer join Card c "
			+ "on d.id = c.deck.id "
			+ "left outer join CardInstance i "
			+ "on c.id = i.card.id "
			+ "left outer join CardPlan p "
			+ "on i.id = p.instance.id "
			+ "where (p.nextDate is null or p.nextDate <= :dateLimit) "
			+ "and d.id = :deckId "
			+ "and c.sideA != '' "
			+ "and c.sideB != '' "
			+ "and i.disabled = false "
			)
	@Transactional(readOnly = true)
	Integer countCardInstancesToReview(
			@Param("deckId") Integer deckId,
			@Param("dateLimit") OffsetDateTime dateLimit);
	
	List<CardInstance> findByCardId(Integer cardId);

	@Query("select i "
			+ "from Deck d "
			+ "left outer join Card c "
			+ "on d.id = c.deck.id "
			+ "left outer join CardInstance i "
			+ "on c.id = i.card.id "
			+ "left outer join CardPlan p "
			+ "on i.id = p.instance.id "
			+ "where (p.nextDate is null or p.nextDate <= :dateLimit) "
			+ "and d.id = :deckId "
			+ "and c.sideA != '' "
			+ "and c.sideB != '' "
			+ "and i.disabled = false "
			+ "order by p.nextDate "
			)
	List<CardInstance> nextCardInstanceToReview(
			@Param("deckId") Integer deckId,
			@Param("dateLimit") OffsetDateTime dateLimit,
			/* @Nullable */ Pageable pageable);
}
