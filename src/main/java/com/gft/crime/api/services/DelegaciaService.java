package com.gft.crime.api.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.crime.api.entities.Delegacia;
import com.gft.crime.api.repositories.DelegaciaRepository;

@Service
public class DelegaciaService {

	@Autowired
	private DelegaciaRepository delegaciaRepository;
	
	public Delegacia atualizar(Long id, Delegacia delegacia) {
		Delegacia delegaciaSalva = buscarDelegaciaPeloId(id);
		BeanUtils.copyProperties(delegacia, delegaciaSalva, "id");
		return delegaciaRepository.save(delegacia);
	}
	
	public Delegacia buscarDelegaciaPeloId(Long id) {
		Optional<Delegacia> delegaciaSalva = delegaciaRepository.findById(id);
		if(delegaciaSalva.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return delegaciaSalva.get();
	}

	
}
