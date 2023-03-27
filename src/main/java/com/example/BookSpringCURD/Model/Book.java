package com.example.BookSpringCURD.Model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book
{
	@Id
	private String id;
	private String uuid="d6a36cff-72c1-4a61-9622-affac180aa27";  
	private long version;
	private String title;
    private String author;
    private String isbn;
    public Book() 
    {
		super();
		// TODO Auto-generated constructor stub
	}
	public Book( String title, String author, String isbn)
    {
    	
    	this.version = Instant.now().toEpochMilli();
        this.title = title;
        this.author = author;
        this.isbn = isbn;
       
    }  
	public Book(String string, String string2) {
		// TODO Auto-generated constructor stub
	}
	public void setVersion(long version) 
	{
		this.version = version;
	}
	public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }
	public String getUuid() 
	{
		return uuid;
	}
	public long getVersion() 
	{
		return version;
	}
	public void setUuid(String uuid) 
	{
		this.uuid = uuid;
	}
	public String getTitle ()
    {
        return title;
    }
	public void setTitle (String title)
    {
        this.title = title;
    }
    public String getAuthor ()
    {
        return author;
    }
    public void setAuthor (String author)
    {
        this.author = author;
    }
    public String getIsbn ()
    {
        return isbn;
    }
    public void setIsbn (String isbn)
    {
        this.isbn = isbn;
    }
}


