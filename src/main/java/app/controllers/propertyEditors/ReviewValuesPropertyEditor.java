package app.controllers.propertyEditors;

import java.beans.PropertyEditorSupport;

import app.model.planner.ReviewValues;

public class ReviewValuesPropertyEditor extends PropertyEditorSupport
{
	@Override
	public void setAsText(String text) throws IllegalArgumentException
	{
		int ordinal = 0;
		try
		{
			ordinal = Integer.parseInt(text);
		}
		catch (Exception e)
		{
			//
		}
		ReviewValues value = ReviewValues.getFromOrdinal(ordinal);
		setValue(value);
	}
	
	@Override
	public String getAsText()
	{
		int ordinal = 0;
		ReviewValues value = (ReviewValues) getValue();
		if (value != null)
		{
			ordinal = value.ordinal();
		}
		return Integer.toString(ordinal);
	}
}
