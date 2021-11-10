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

import com.gft.crime.api.entities.Prisao;
import com.gft.crime.api.event.RecursoCriadoEvent;
import com.gft.crime.api.repositories.PrisaoRepository;
import com.gft.crime.api.services.PrisaoService;

@RestController
@RequestMapping("/prisao")
public class PrisaoController {

	@Autowired
	private PrisaoService prisaoService;
	@Autowired
	private PrisaoRepository prisaoRepository;
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Prisao> listar() {
		return prisaoRepository.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('DELEGADO')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Prisao> criar(@Valid @RequestBody Prisao prisao, HttpServletResponse response) {
		Prisao prisaoSalva = prisaoRepository.save(prisao);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, prisaoSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(prisaoSalva);
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasAuthority('ADVOGADO')")
	public Prisao buscarPeloId(Long id) throws Exception {
		Optional<Prisao> prisao = prisaoRepository.findById(id);
		if (prisao.isEmpty()) {
			throw new Exception("Prisão não encontrada");
		}
		return prisao.get();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		prisaoRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('JUIZ', 'DELEGADO')")
	public ResponseEntity<Prisao> atualizar(@PathVariable Long id, @Valid @RequestBody Prisao prisao) {
		Prisao prisaoSalva = prisaoService.atualizar(id, prisao);
		return ResponseEntity.ok(prisaoSalva);
	}
}
