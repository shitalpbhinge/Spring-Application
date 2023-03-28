package com.example.BookSpringCURD.Controller;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.fields;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import  org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import  org.springframework.data.mongodb.core.query.Criteria.*;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema.ConflictResolutionFunction.Path;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.BookSpringCURD.BookSpringCurdApplication;
import com.example.BookSpringCURD.Model.Author;
import com.example.BookSpringCURD.Model.Book;
import com.example.BookSpringCURD.Model.EmailRequest;
import com.example.BookSpringCURD.Model.FileUploadHelper;
import com.example.BookSpringCURD.Repository.BookRepository;
import com.example.BookSpringCURD.services.EmailService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.reactivestreams.client.MongoCollection;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.domain.Sort;
@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/v1/mongodbapp")
public class BookController
{
		private static final Logger logger = LoggerFactory.getLogger(BookSpringCurdApplication .class);
	    @Autowired
	    BookRepository bookRepository;
 
	    @Autowired
	    MongoTemplate mongoTemplate;
	    
	    @Autowired
	    EmailService emailService;
	    
	    @Autowired
		private FileUploadHelper fileUploadHelper;
		private final ResourceLoader resourceLoader;
		
		 @Autowired
		    public BookController(ResourceLoader resourceLoader) {
		        this.resourceLoader = resourceLoader;
		    }
	    @PostMapping("/uploadFile")
		public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file)
		{
			
			try
			{
			//validation
			if(file.isEmpty())
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request must contain a file");
			}
			//
			if(file.getContentType().equals("image/jpeg"))
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("only jpeg");
			}
			//upload file
			boolean f=fileUploadHelper.uploadFile(file);
			if(f) 
			{
				return ResponseEntity.ok("File Uploaded.....");
			}
			
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("try agrain...");
			
		}
	    @GetMapping("/directoryStructure")
        public List<List<String>> getDirectory(@RequestParam("path") String path) throws Exception {
           
         logger.info("inside method get directory");
           
         logger.info("acquiring directory path");
            File directory = new File(path);
         logger.info("directory path acquired");
         logger.info("Getting contents of directory");
            String[] contents = directory.list();
            if (contents == null) {
          logger.error("Failed to acquire content of directory");
                throw new Exception("Failed to get contents of directory: " + path);
            }
           logger.info("content acquired");
            List<String> folders = new ArrayList<>();
            List<String> files = new ArrayList<>();
            logger.info("sorting content into file and directory");
            for (String content : contents) {
                File f = new File(directory, content);
                if (f.isDirectory()) {
                    folders.add(content);
                } else if (f.isFile()) {
                    files.add(content);
                }
            }
            List<List<String>> result = new ArrayList<>();
            result.add(folders);
            result.add(files);
           logger.info("Sorting success");
            return result;
        }
	    //this api send simple email
	    @PostMapping("/sendingemail")
	    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request)
	    {
	    	//logger.info("mail send Successfully.");
	        System.out.println(request);
	        

	        boolean result = this.emailService.sendEmail(request.getSubject(), request.getMessage(), request.getTo());

	        if(result){

	           return  ResponseEntity.ok("Email Properly Sent Successfully... ");

	        }else{

	            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("email sending fail");
	        }
	    }

	    //this api send email with file
	  @PostMapping("/sendemailattachement")
	    public ResponseEntity<?> sendEmailWithAttachment(@RequestBody EmailRequest request)
	    {
	    	//logger.info("Sent Email With Attchment Successfully... ");
	        System.out.println(request);

	        boolean result = this.emailService.sendWithAttachment(request.getSubject(), request.getMessage(), request.getTo());

	        if(result){

	            return  ResponseEntity.ok("Sent Email With Attchment Successfully... ");

	        }else{

	            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("attachment email sending fail");
	        }

	    }
	
	    @GetMapping("/getCollectionNames")
	    
	    public Set<String> getallCollection()
	    {
	    	logger.info("getting all collections....");
	        Set<String> collections = mongoTemplate.getCollectionNames();
	        Set<String> filteredCollections= new HashSet<String>();
	        for(String collection: collections) 
	        {
	                if (collection.contains(collection)) 
	                {
	                    filteredCollections.add(collection);
	                }
	            }
	        return filteredCollections;
	    }
	   @GetMapping("/getBooks")
	   public Object getAllAuthors() throws JsonProcessingException
	   {
		   	   logger.info("getting List of All Authors.....");
			   List<Book> books = mongoTemplate.findAll(Book.class, "books");
			   Author author=new Author();
			   
			   List<Author> authors = books.stream()
				      .collect(Collectors.groupingBy(Book::getAuthor))
				      .entrySet().stream()
				      .map(e -> new Author(
				        e.getKey(),
				        e.getValue(),
				        e.getValue().size()
				      ))
				      .collect(Collectors.toList());
	
				    Map<String, List<Author>> response = new HashMap<>();
				    response.put("author", authors);
				    
				    ObjectMapper mapper = new ObjectMapper();
				   
					String json = mapper.writeValueAsString(response);
					return json;
				
	    }
	    @GetMapping("/books")    
	    public ResponseEntity<List> getAllBooks(@RequestParam(required = false) String bookTitle,String bookAuther)
	    {
		        try
		        {
		        		logger.info("getting All books");
			            List listOfBooks = new ArrayList<>();
			            if(bookTitle == null || bookTitle.isEmpty())
			            {
			                bookRepository.findAll().forEach(listOfBooks::add);
			            }
			            else
			            {
			                bookRepository.findByTitleContaining(bookTitle).forEach(listOfBooks::add);
			              
			            }
			            if(listOfBooks.isEmpty())
			            {
			                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			            }
			            return new ResponseEntity<>(listOfBooks, HttpStatus.OK);
		        }
		        catch (Exception e)
		        {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
	    } 
	   @GetMapping("/getLatestByUuid") 
	   public Object getLatestByAppUuid(@RequestParam(value = "uuid", required = false)  String appUuid)
	   {
		   		logger.info("getting Latest Uuid");
		    	AggregationResults<Book> ruleExecResults = mongoTemplate.aggregate(newAggregation(match(new Criteria("uuid").is(appUuid)),
		    			sort(Sort.by(Sort.Direction.DESC,"version")),limit(Integer.parseInt("1"))),"books", Book.class);
		    	
		    	Object book =  ruleExecResults.getMappedResults();
				return  book;
				
		}
	   @GetMapping("/getBookByid/{id}")
	    public ResponseEntity getBookByUuId(@PathVariable("id") String id)
	    {
		        try
		        {
		        	logger.info("getting Books by their id");
		            Optional bookOptional = bookRepository.findById(id);
		            
		            return new ResponseEntity<>( bookOptional.get(), HttpStatus.OK);
		           
		        }
		        catch (Exception e)
		        {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
	    }	
	    @PostMapping("/books")
	    public ResponseEntity addABookToLibrary(@RequestBody Book book)
	    {
		        try
		        {
		        	logger.info("Adding book to Library database......");
		            Book createdBook = bookRepository.save(new Book(book.getTitle(),book.getAuthor(),book.getIsbn()));
		            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
		        }
		        catch (Exception e)
		        {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
	    } 
	    @PutMapping("/books/{id}")
	    public ResponseEntity updateVersionOfSameUuid(@PathVariable("id") String uuid, @RequestBody Book book)
	    {
	    		logger.info("Update the book by id.....");
		        Optional bookOptional = bookRepository.findById(uuid);
		        
		        if(bookOptional.isPresent())
		        {
			        	long version2=Instant.now().toEpochMilli();
			            Book updatedBook = (Book) bookOptional.get();
			            updatedBook.setTitle(book.getTitle());
			            updatedBook.setAuthor(book.getAuthor());
			            updatedBook.setIsbn(book.getIsbn());
			            updatedBook.setVersion(version2);
			            updatedBook = bookRepository.save(new Book(updatedBook.getTitle(), updatedBook.getAuthor(),updatedBook.getIsbn()));
			            
			            return ResponseEntity.ok(updatedBook);
		        }
		        else
		        {
		            	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
				
	    }   
		@DeleteMapping("/books/{id}")
	    public ResponseEntity<String> deleteABook(@PathVariable("id") String id)
	    {
		        try
		        {
		        		logger.info("Deleting book by id......");
			            bookRepository.deleteById(id);
			            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		        }
		        catch (Exception e)
		        {
		            	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		        }
	    }	
}
