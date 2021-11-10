package com.gft.crime.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.crime.api.entities.Usuario;
import com.gft.crime.api.services.EmailService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController{
	
	@Autowired
	public EmailService emailService;
	
	@GetMapping("usuario")
	public ResponseEntity<String> metodoGetUsuario() {

		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(usuario);

		if (usuario.getPerfil().getNomePerfil().equals("JUIZ")) {
			return ResponseEntity.ok("Usuário é um Juiz");
		} else if (usuario.getPerfil().getNomePerfil().equals("ADVOGADO")) {
			emailService.sendEmail(usuario);
			return ResponseEntity.ok("Usuário é um Advogado");
		} else if (usuario.getPerfil().getNomePerfil().equals("DELEGADO")) {
			return ResponseEntity.ok("Usuário é um Delegado");
		} else {
			return new ResponseEntity<>("Usuário não identificado", HttpStatus.BAD_REQUEST);
		}
	}
}
