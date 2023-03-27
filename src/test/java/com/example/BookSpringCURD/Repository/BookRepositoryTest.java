package com.example.BookSpringCURD.Repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.BookSpringCURD.Model.Book;
@SpringBootTest
class BookRepositoryTest 
{
	@Autowired
	private BookRepository bookRepository;
	/*@Test
	void isBookExistByAuthor()
	{
		Book book=new Book("C programming", "E Balguruswammi", "12345");
		bookRepository.save(book);
		List actualresult=bookRepository.findByAuthor("E Balguruswammi");
		assertThat(actualresult);
	}*/
	@BeforeEach
	void setUp() throws Exception 
	{
		
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		
	}

	@Test
	void test() 
	{
		Book book=new Book("C programming", "E Balguruswammi", "12345");
		bookRepository.save(book);
		List actualresult=bookRepository.findByAuthor("E Balguruswammi");
		assertThat(actualresult);
	}

}
