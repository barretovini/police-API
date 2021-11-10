package com.gft.crime.api.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.crime.api.entities.Vitima;
import com.gft.crime.api.repositories.VitimaRepository;

@Service
public class VitimaService {

	@Autowired
	private VitimaRepository vitimaRepository;
	
	public Vitima atualizar(Long id, Vitima vitima) {
		Vitima vitimaSalva = buscarVitimaPeloId(id);
		BeanUtils.copyProperties(vitima, vitimaSalva, "id");
		return vitimaRepository.save(vitima);
	}
	
	public Vitima buscarVitimaPeloId(Long id) {
		Optional<Vitima> vitimaSalva = vitimaRepository.findById(id);
		if(vitimaSalva.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return vitimaSalva.get();
	}

	
}
