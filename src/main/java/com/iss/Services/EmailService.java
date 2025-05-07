// Java Program to Illustrate Creation Of
// Service implementation class

package com.iss.Services;

import com.iss.models.EmailDetails;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

// Annotation
@Service

public class EmailService {

    @Autowired private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;


    @Async
    public void sendSimpleMail(EmailDetails details)
    {

        try {

            SimpleMailMessage mailMessage
                = new SimpleMailMessage();

            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            
            javaMailSender.send(mailMessage);
            System.out.println("SENT");
            return ;
        }

        catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e.getMessage());
        }
    }

    @Async
	public void sendMailWithAttachment(EmailDetails details) {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		try {

	        mimeMessageHelper= new MimeMessageHelper(mimeMessage, true);
	        mimeMessageHelper.setTo(details.getRecipient());
	        mimeMessageHelper.setText(details.getMsgBody());
	        mimeMessageHelper.setSubject(
	            details.getSubject());
	       
	
	        mimeMessageHelper.addAttachment("Innovative.pdf",new ByteArrayDataSource(details.getAttachment(),"application/pdf"));
	
	        javaMailSender.send(mimeMessage);
	        return ;
	    }
	
	    catch (MessagingException e) {
	    	e.printStackTrace();
	    	throw new RuntimeException(e.getMessage());
	    }
	}
    
}
