package com.gft.crime.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.gft.crime.api.entities.Usuario;

@Service
public class EmailService {

	private JavaMailSender javaMailSender;
	
	@Autowired
	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendEmail(Usuario usuario) throws MailException {

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(usuario.getEmail());
		mail.setSubject("Login API");
		mail.setText("WOW Você fez login na API");

		javaMailSender.send(mail);
	}

}
