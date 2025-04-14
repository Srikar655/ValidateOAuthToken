package com.iss.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iss.models.EmailDetails;
import com.iss.Services.EmailService;

@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired private EmailService emailService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("/sendMail")
    public Map<String, String> sendMail(@RequestBody EmailDetails details)
    {
    	try {
        emailService.sendSimpleMail(details);
        return Map.of("Status","MAIL SENT");
    	}
	      catch(Exception ex)
	      {
	    	  return Map.of("Status","MAIL NOT SENT SORRY!");
	      }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("/sendMailWithAttachments")
    public Map<String, String> sendMailWithAttachment(@RequestBody EmailDetails details)
    {
    	try {
	         emailService.sendMailWithAttachment(details);
	
	         return Map.of("Status","MAIL SENT");
    	}
	      catch(Exception ex)
	      {
	    	  return Map.of("Status","MAIL NOT SENT SORRY!");
	      }
    }
}
