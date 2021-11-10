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

import com.gft.crime.api.entities.Legista;
import com.gft.crime.api.event.RecursoCriadoEvent;
import com.gft.crime.api.repositories.LegistaRepository;
import com.gft.crime.api.services.LegistaService;

@RestController
@RequestMapping("/legista")
public class LegistaController {

	@Autowired
	private LegistaRepository legistaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private LegistaService legistaService;

	@GetMapping
	public List<Legista> listar() {
		return legistaRepository.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Legista> criar(@Valid @RequestBody Legista legista, HttpServletResponse response) {
		Legista legistaSalva = legistaRepository.save(legista);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, legistaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(legistaSalva);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ADVOGADO')")
	public Legista buscarPeloId(Long id) throws Exception {
		Optional<Legista> legista = legistaRepository.findById(id);
		if (legista.isEmpty()) {
			throw new Exception("Legista não encontrada");
		}
		return legista.get();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		legistaRepository.deleteById(id);
	}

	// PRECISA MANTER O ID DESCRIMINADO QUANDO FOR EDITAR
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	public ResponseEntity<Legista> legista(@PathVariable Long id, @Valid @RequestBody Legista legista) {
		Legista legistaSalva = legistaService.atualizar(id, legista);
		return ResponseEntity.ok(legistaSalva);
	}
}
