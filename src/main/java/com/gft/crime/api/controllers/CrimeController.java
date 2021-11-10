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

import com.gft.crime.api.entities.Crime;
import com.gft.crime.api.event.RecursoCriadoEvent;
import com.gft.crime.api.repositories.CrimeRepository;
import com.gft.crime.api.services.CrimeService;

@RestController
@RequestMapping("/crime")
public class CrimeController {

	@Autowired
	private CrimeService crimeService;
	@Autowired
	private CrimeRepository crimeRepository;
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Crime> listar() {
		return crimeRepository.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Crime> criar(@Valid @RequestBody Crime crime, HttpServletResponse response) {
		Crime crimeSalva = crimeRepository.save(crime);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, crimeSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(crimeSalva);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ADVOGADO')")
	public Crime buscarPeloId(Long id) throws Exception {
		Optional<Crime> crime = crimeRepository.findById(id);
		if (crime.isEmpty()) {
			throw new Exception("Crime não encontrado");
		}
		return crime.get();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		crimeRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	public ResponseEntity<Crime> atualizar(@PathVariable Long id, @Valid @RequestBody Crime crime) {
		Crime crimeSalva = crimeService.atualizar(id, crime);
		return ResponseEntity.ok(crimeSalva);
	}
}
