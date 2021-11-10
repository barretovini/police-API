package com.gft.crime.api.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.crime.api.entities.Delegacia;
import com.gft.crime.api.entities.Delegado;
import com.gft.crime.api.repositories.DelegaciaRepository;
import com.gft.crime.api.repositories.DelegadoRepository;
import com.gft.crime.api.services.exception.DelegaciaInexistenteException;

@Service
public class DelegadoService {
	
	@Autowired
	private DelegadoRepository delegadoRepository;
	
	@Autowired
	private DelegaciaRepository delegaciaRepository;
	
	
	public Delegado atualizar(Long id, Delegado delegado) {
		Delegado DelegadoSalva = buscarDelegaciaPeloId(id);
		BeanUtils.copyProperties(delegado, DelegadoSalva, "id");
		return delegadoRepository.save(delegado);
	}
	
	private Delegado buscarDelegaciaPeloId(Long id) {
		Optional<Delegado> delegadoSalva = delegadoRepository.findById(id);
		if(delegadoSalva.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return delegadoSalva.get();
	}

	
	public Delegado salvar(@Valid Delegado delegado) {
		
		Optional<Delegacia> delegacia = delegaciaRepository.findById(delegado.getDelegacia().getId());
		if(delegacia.isEmpty()) {
			throw new DelegaciaInexistenteException();
		}
		return delegadoRepository.save(delegado);
	}

}
