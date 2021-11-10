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

import com.gft.crime.api.entities.Criminoso;
import com.gft.crime.api.event.RecursoCriadoEvent;
import com.gft.crime.api.repositories.CriminosoRepository;
import com.gft.crime.api.services.CriminosoService;

@RestController
@RequestMapping("/criminoso")
public class CriminosoController {

	@Autowired
	private CriminosoRepository criminosoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private CriminosoService criminosoService;

	@GetMapping
	public List<Criminoso> listar() {
		return criminosoRepository.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Criminoso> criar(@Valid @RequestBody Criminoso criminoso, HttpServletResponse response) {
		Criminoso criminosoSalva = criminosoRepository.save(criminoso);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, criminosoSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(criminosoSalva);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ADVOGADO')")
	public Criminoso buscarPeloId(Long id) throws Exception {
		Optional<Criminoso> criminoso = criminosoRepository.findById(id);
		if (criminoso.isEmpty()) {
			throw new Exception("Criminoso não encontrado");
		}
		return criminoso.get();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	public void remover(@PathVariable Long id) {
		criminosoRepository.deleteById(id);
	}

	// PRECISA MANTER O ID DESCRIMINADO QUANDO FOR EDITAR
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	public ResponseEntity<Criminoso> atualizar(@PathVariable Long id, @Valid @RequestBody Criminoso criminoso) {
		Criminoso criminosoSalva = criminosoService.atualizar(id, criminoso);
		return ResponseEntity.ok(criminosoSalva);
	}
}
