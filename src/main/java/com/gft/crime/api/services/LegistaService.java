package com.gft.crime.api.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.crime.api.entities.Legista;
import com.gft.crime.api.repositories.LegistaRepository;

@Service
public class LegistaService {

	@Autowired
	private LegistaRepository legistaRepository;
	
	public Legista atualizar(Long id, Legista legista) {
		Legista legistaSalva = buscarLegistaPeloId(id);
		BeanUtils.copyProperties(legista, legistaSalva, "id");
		return legistaRepository.save(legista);
	}
	
	public Legista buscarLegistaPeloId(Long id) {
		Optional<Legista> legistaSalva = legistaRepository.findById(id);
		if(legistaSalva.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return legistaSalva.get();
	}

	
}
