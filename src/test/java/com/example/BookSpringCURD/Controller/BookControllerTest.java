package com.example.BookSpringCURD.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
@SpringBootTest
class BookControllerTest 
{
	@Mock
    private MongoTemplate mongoTemplate;
	
	 @InjectMocks
	private BookController bookController;
	 
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testGetAllCollection() {
        Set<String> collections = new HashSet<>(Arrays.asList("collection1", "collection2", "other_collection"));
        when(mongoTemplate.getCollectionNames()).thenReturn(collections);
        
        Set<String> filteredCollections = bookController.getallCollection();
        
        assertEquals(2, filteredCollections.size());
        assertEquals(true, filteredCollections.contains("collection1"));
        assertEquals(true, filteredCollections.contains("collection2"));
    }
	

}
