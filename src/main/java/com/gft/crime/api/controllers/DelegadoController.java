package com.gft.crime.api.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.crime.api.entities.Delegado;
import com.gft.crime.api.event.RecursoCriadoEvent;
import com.gft.crime.api.repositories.DelegadoRepository;
import com.gft.crime.api.services.DelegadoService;

@RestController
@RequestMapping("/delegado")
public class DelegadoController {

	@Autowired
	private DelegadoService delegadoService;
	@Autowired
	private DelegadoRepository delegadoRepository;
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Delegado> listar() {
		return delegadoRepository.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Delegado> criar(@Valid @RequestBody Delegado delegado, HttpServletResponse response) {
		Delegado delegadoSalva = delegadoRepository.save(delegado);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, delegadoSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(delegadoSalva);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ADVOGADO')")
	public Delegado buscarPeloId(Long id) throws Exception {
		Optional<Delegado> delegado = delegadoRepository.findById(id);
		if (delegado.isEmpty()) {
			throw new Exception("Delegado não encontrado");
		}
		return delegado.get();
	}

	@GetMapping("/asc")
	public Page<Delegado> getDelegadosAsc(@RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy) {
		return delegadoRepository.findAll(PageRequest.of(page.orElse(0), 5, Sort.Direction.ASC, sortBy.orElse("id")));
	}

	@GetMapping("/desc")
	public Page<Delegado> getDelegadosDesc(@RequestParam Optional<Integer> page,
			@RequestParam Optional<String> sortBy) {
		return delegadoRepository.findAll(PageRequest.of(page.orElse(0), 5, Sort.Direction.DESC, sortBy.orElse("id")));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		delegadoRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	public ResponseEntity<Delegado> atualizar(@PathVariable Long id, @Valid @RequestBody Delegado delegado) {
		Delegado delegadoSalva = delegadoService.atualizar(id, delegado);
		return ResponseEntity.ok(delegadoSalva);
	}
}
