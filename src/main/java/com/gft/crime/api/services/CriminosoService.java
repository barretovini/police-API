package com.gft.crime.api.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.crime.api.entities.Criminoso;
import com.gft.crime.api.repositories.CriminosoRepository;

@Service
public class CriminosoService {

	@Autowired
	private CriminosoRepository criminosoRepository;
	
	public Criminoso atualizar(Long id, Criminoso criminoso) {
		Criminoso criminosoSalva = buscarCriminosoPeloId(id);
		BeanUtils.copyProperties(criminoso, criminosoSalva, "id");
		return criminosoRepository.save(criminoso);
	}
	
	public Criminoso buscarCriminosoPeloId(Long id) {
		Optional<Criminoso> criminosoSalva = criminosoRepository.findById(id);
		if(criminosoSalva.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return criminosoSalva.get();
	}

	
}
