package com.example.BookSpringCURD.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.BookSpringCURD.Model.Book;
@Repository
public interface BookRepository extends MongoRepository<Book, String>
{
	List findByTitleContaining(String title);
	List findByAuthor(String name);	
}