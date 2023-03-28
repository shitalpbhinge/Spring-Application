package com.example.BookSpringCURD.Controller;
/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@EnableWebMvc
public class EmailController{
	
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
	@Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/mail")
    public ResponseEntity<String> sendEmail(@RequestParam("to") String to,
       @RequestParam("subject") String subject,@RequestParam("message") String message) {
    	
          logger.info("Entering Method sendEmail");
        
          
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        logger.info("Retrieved mail details");
        logger.info("send mail to : {} ",to);
        try 
        {
            javaMailSender.send(mailMessage);
        logger.info("Mail sent successfully");
            return ResponseEntity.ok("Email sent successfully!");
        } catch (Exception e) {
        	logger.error("Failed to send mail");   
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }
}*/
