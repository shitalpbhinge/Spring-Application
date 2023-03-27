package com.example.BookSpringCURD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@ComponentScan
public class BookSpringCurdApplication  
{
	private static final Logger logger = LoggerFactory.getLogger(BookSpringCurdApplication .class);
	public static void main(String[] args) 
	{
		  SpringApplication.run(BookSpringCurdApplication .class, args);
		  logger.info("This is a info message");
		  logger.debug("This is a debug message");
		  logger.warn("This is a warn message");
		  logger.error("This is a error message");
		 
	}
}
