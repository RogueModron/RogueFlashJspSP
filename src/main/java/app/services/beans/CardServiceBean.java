package app.services.beans;

import java.io.Serializable;

import app.model.entities.Card;
import app.model.entities.CardInstance;

public class CardServiceBean implements Serializable
{
	private static final long serialVersionUID = 1L;


	private Card card = null;
	private CardInstance sideAToB = null;
	private CardInstance sideBToA = null;


	public CardServiceBean(
			Card card,
			CardInstance sideAToB,
			CardInstance sideBToA)
	{
		this.card = card;
		this.sideAToB = sideAToB;
		this.sideBToA = sideBToA;
	}


	public Card getCard()
	{
		return card;
	}

	public CardInstance getSideAToB()
	{
		return sideAToB;
	}

	public CardInstance getSideBToA()
	{
		return sideBToA;
	}
}
