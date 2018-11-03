package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.entities.CardReview;

public interface CardReviewRepository extends JpaRepository<CardReview, Integer>
{

}
