package com.gft.crime.api.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.gft.crime.api.entities.Policial;
import com.gft.crime.api.event.RecursoCriadoEvent;
import com.gft.crime.api.repositories.PolicialRepository;
import com.gft.crime.api.services.PolicialService;

@RestController
@RequestMapping("/policial")
public class PolicialController {

	@Autowired
	private PolicialRepository policialRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private PolicialService policialService;

	@GetMapping
	public List<Policial> listar() {
		return policialRepository.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Policial> criar(@Valid @RequestBody Policial policial, HttpServletResponse response) {
		Policial policialSalva = policialRepository.save(policial);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, policialSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(policialSalva);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ADVOGADO')")
	public Policial buscarPeloId(Long id) throws Exception {
		Optional<Policial> policial = policialRepository.findById(id);
		if (policial.isEmpty()) {
			throw new Exception("Policial não encontrado");
		}
		return policial.get();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		policialRepository.deleteById(id);
	}

	// PRECISA MANTER O ID DESCRIMINADO QUANDO FOR EDITAR
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	public ResponseEntity<Policial> policial(@PathVariable Long id, @Valid @RequestBody Policial policial) {
		Policial policialSalva = policialService.atualizar(id, policial);
		return ResponseEntity.ok(policialSalva);
	}

	@GetMapping("/nome/{nome}")
	public Page<Policial> findByNome(@PathVariable("nome") String nome, Pageable pageable) {

		if (nome == null) {
			return policialRepository.findAll(pageable);
		} else {
			return policialRepository.findByNome(nome, pageable);
		}

	}
}