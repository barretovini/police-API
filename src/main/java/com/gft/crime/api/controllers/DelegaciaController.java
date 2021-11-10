package com.gft.crime.api.controllers;


import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.crime.api.entities.Delegacia;
import com.gft.crime.api.event.RecursoCriadoEvent;
import com.gft.crime.api.repositories.DelegaciaRepository;
import com.gft.crime.api.services.DelegaciaService;

@RestController
@RequestMapping("/delegacia")
public class DelegaciaController {

	@Autowired
	private DelegaciaRepository delegaciaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private DelegaciaService delegaciaService;

	@GetMapping
	public List<Delegacia> listar() {
		return delegaciaRepository.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Delegacia> criar(@Valid @RequestBody Delegacia delegacia, HttpServletResponse response) {
		Delegacia delegaciaSalva = delegaciaRepository.save(delegacia);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, delegaciaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(delegaciaSalva);
	}

	@GetMapping("/{id}")
	//@PreAuthorize("hasAuthority('ADVOGADO')")
	public Delegacia buscarPeloId(Long id) throws Exception {
		Optional<Delegacia> delegacia = delegaciaRepository.findById(id);
		if (delegacia.isEmpty()) {
			throw new Exception("Delegacia não encontrada");
		}
		return delegacia.get();
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		delegaciaRepository.deleteById(id);
	}

	//PRECISA MANTER O ID DESCRIMINADO QUANDO FOR EDITAR
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	public ResponseEntity<Delegacia> atualizar(@PathVariable Long id, @Valid @RequestBody Delegacia delegacia){
		Delegacia delegaciaSalva = delegaciaService.atualizar(id, delegacia);
		return ResponseEntity.ok(delegaciaSalva);
	}
}
