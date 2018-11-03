package app.model.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CardsInstances")
@org.hibernate.annotations.DynamicUpdate(value = true)
public class CardInstance implements Serializable
{
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = 0;

	@ManyToOne
	@JoinColumn(name = "cardId")
	private Card card = null;
	
	@OneToOne(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			orphanRemoval = true,
			mappedBy = "instance")
	private CardPlan plan = null;

	private boolean sideAToB = false;
	private boolean disabled = false;


	protected CardInstance()
	{
		//
	}

	public CardInstance(Card card)
	{
		this.card = card;

		this.plan = new CardPlan(this);
	}


	public int getId()
	{
		return id;
	}

	public Card getCard()
	{
		return card;
	}

	public CardPlan getPlan()
	{
		return plan;
	}

	public boolean isSideAToB()
	{
		return sideAToB;
	}

	public void setSideAToB(boolean sideAToB)
	{
		this.sideAToB = sideAToB;
	}

	public boolean isDisabled()
	{
		return disabled;
	}

	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}
}
