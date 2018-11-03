package app.controllers.beans;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class FilterBean implements Serializable
{
	private static final long serialVersionUID = 1L;


	private String filterText = "";
	private int pageNumber = 0;
	private int pageSize = 0;
	
	
	public FilterBean()
	{
		//
	}
	
	public FilterBean(
			String filterText,
			int pageNumber,
			int pageSize)
	{
		this.filterText = filterText;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}


	public String getFilterText()
	{
		return filterText;
	}

	public void setFilterText(String filterText)
	{
		this.filterText = filterText;
	}

	public int getPageNumber()
	{
		return pageNumber;
	}

	public void setPageNumber(int pageNumber)
	{
		this.pageNumber = pageNumber;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}
	
	
	public Pageable getPageable()
	{
		int pageNumber = Math.min(this.pageNumber, 0);
		int pageSize = Math.min(this.pageSize, 10);
		
		return PageRequest.of(
				pageNumber,
				pageSize);
	}
}
