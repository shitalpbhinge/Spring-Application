package com.example.BookSpringCURD.Model;
import java.util.List;
public class Author 
{
	private String name;
    private List<Book> book;
    private long totalCount;     
    public Author() 
    {
		super();
	}
	public Author(String name, List<Book> book, long totalCount) 
    {
		super();
		this.name = name;
		this.book = book;
		this.totalCount = totalCount;
	}
   	public void setName(String name) 
   	{
		this.name = name;
	}
	public String getName() 
    { 
    	return name; 
    }
    public void setBook(List<Book> book) 
	{
		this.book = book;
	}
	public List<Book> getBook() 
	{
		return book;
	}
	public long getTotalCount() 
    { 
    	return totalCount; 
    }
    public void setTotalCount(long value) 
    { 
    	this.totalCount = value; 
    }
	}