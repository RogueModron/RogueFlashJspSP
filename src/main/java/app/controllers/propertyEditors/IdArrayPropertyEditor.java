package app.controllers.propertyEditors;

import java.beans.PropertyEditorSupport;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class IdArrayPropertyEditor extends PropertyEditorSupport
{
	// See StringArrayPropertyEditor.
	
	
	private final String SEPARATOR = ",";
	
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String[] stringArray = StringUtils.delimitedListToStringArray(
				text,
				SEPARATOR);
		stringArray = StringUtils.trimArrayElements(stringArray);
		
		Set<Integer> idsSet = new HashSet<>();
		for (String s : stringArray)
		{
			try
			{
				Integer id = Integer.parseUnsignedInt(s);
				idsSet.add(id);
			}
			catch (NumberFormatException e)
			{
				//
			}
		}
		if (idsSet.size() == 0)
		{
			setValue(null);
		}
		else
		{
			setValue(idsSet.toArray(new Integer[] {}));
		}
	}
	
	@Override
	public String getAsText() {
		return StringUtils.arrayToDelimitedString(
				ObjectUtils.toObjectArray(getValue()),
				SEPARATOR);
	}
}
