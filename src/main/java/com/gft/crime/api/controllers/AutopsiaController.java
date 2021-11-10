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

import com.gft.crime.api.entities.Autopsia;
import com.gft.crime.api.event.RecursoCriadoEvent;
import com.gft.crime.api.repositories.AutopsiaRepository;
import com.gft.crime.api.services.AutopsiaService;

@RestController
@RequestMapping("/autopsia")
public class AutopsiaController {

	@Autowired
	private AutopsiaService autopsiaService;
	@Autowired
	private AutopsiaRepository autopsiaRepository;
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Autopsia> listar() {
		return autopsiaRepository.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('DELEGADO')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Autopsia> criar(@Valid @RequestBody Autopsia autopsia, HttpServletResponse response) {
		Autopsia autopsiaSalva = autopsiaRepository.save(autopsia);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, autopsiaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(autopsiaSalva);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ADVOGADO')")
	public Autopsia buscarPeloId(Long id) throws Exception {
		Optional<Autopsia> autopsia = autopsiaRepository.findById(id);
		if (autopsia.isEmpty()) {
			throw new Exception("Autopsia não encontrada");
		}
		return autopsia.get();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		autopsiaRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	public ResponseEntity<Autopsia> atualizar(@PathVariable Long id, @Valid @RequestBody Autopsia autopsia) {
		Autopsia autopsiaSalva = autopsiaService.atualizar(id, autopsia);
		return ResponseEntity.ok(autopsiaSalva);
	}
}
