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

import com.gft.crime.api.entities.Vitima;
import com.gft.crime.api.event.RecursoCriadoEvent;
import com.gft.crime.api.repositories.VitimaRepository;
import com.gft.crime.api.services.VitimaService;

@RestController
@RequestMapping("/vitima")
public class VitimaController {

	@Autowired
	private VitimaRepository vitimaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private VitimaService vitimaService;

	@GetMapping
	public List<Vitima> listar() {
		return vitimaRepository.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Vitima> criar(@Valid @RequestBody Vitima vitima, HttpServletResponse response) {
		Vitima vitimaSalva = vitimaRepository.save(vitima);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, vitimaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(vitimaSalva);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ADVOGADO')")
	public Vitima buscarPeloId(Long id) throws Exception {
		Optional<Vitima> vitima = vitimaRepository.findById(id);
		if (vitima.isEmpty()) {
			throw new Exception("Vitima não encontrada");
		}
		return vitima.get();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		vitimaRepository.deleteById(id);
	}

	// PRECISA MANTER O ID DESCRIMINADO QUANDO FOR EDITAR
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	public ResponseEntity<Vitima> vitima(@PathVariable Long id, @Valid @RequestBody Vitima vitima) {
		Vitima vitimaSalva = vitimaService.atualizar(id, vitima);
		return ResponseEntity.ok(vitimaSalva);
	}
}
