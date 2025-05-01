package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Mail;

@Service
public interface MailService {
	
	public void sendEmail(Mail mail);


}
