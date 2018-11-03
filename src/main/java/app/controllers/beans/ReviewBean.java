package app.controllers.beans;

import java.io.Serializable;

public class ReviewBean implements Serializable
{
	private static final long serialVersionUID = 1L;


	private int cardId = 0;
	private String sideA = "";
	private String sideB = "";
	private String notes = "";
	private String tags = "";
	private int cardInstanceId = 0;
	private boolean sideAToB = false;


	public ReviewBean()
	{
		//
	}

	public ReviewBean(
			int cardId,
			String sideA,
			String sideB,
			String notes,
			String tags,
			int cardInstanceId,
			boolean sideAToB)
	{
		this.cardId = cardId;
		this.sideA = sideA;
		this.sideB = sideB;
		this.notes = notes;
		this.tags = tags;
		this.cardInstanceId = cardInstanceId;
		this.sideAToB = sideAToB;
	}


	public int getCardId()
	{
		return cardId;
	}

	public void setCardId(int cardId)
	{
		this.cardId = cardId;
	}

	public String getSideA()
	{
		return sideA;
	}

	public void setSideA(String sideA)
	{
		this.sideA = sideA;
	}

	public String getSideB()
	{
		return sideB;
	}

	public void setSideB(String sideB)
	{
		this.sideB = sideB;
	}

	public String getNotes()
	{
		return notes;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	public String getTags()
	{
		return tags;
	}

	public void setTags(String tags)
	{
		this.tags = tags;
	}

	public int getCardInstanceId()
	{
		return cardInstanceId;
	}

	public void setCardInstanceId(int cardInstanceId)
	{
		this.cardInstanceId = cardInstanceId;
	}

	public boolean isSideAToB()
	{
		return sideAToB;
	}

	public void setSideAToB(boolean sideAToB)
	{
		this.sideAToB = sideAToB;
	}
}
