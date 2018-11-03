package app.repositories.projections;

public interface CardSearchItem
{
	Integer getCardId();
	String getSideA();
	String getSideB();
	String getNotes();
	String getTags();
	Boolean getSideAToBDisabled();
}
