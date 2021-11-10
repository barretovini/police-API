package com.gft.crime.api.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.crime.api.entities.Policial;
import com.gft.crime.api.repositories.PolicialRepository;

@Service
public class PolicialService {

	@Autowired
	private PolicialRepository policialRepository;
	
	public Policial atualizar(Long id, Policial policial) {
		Policial policialSalva = buscarPolicialPeloId(id);
		BeanUtils.copyProperties(policial, policialSalva, "id");
		return policialRepository.save(policial);
	}
	
	public Policial buscarPolicialPeloId(Long id) {
		Optional<Policial> policialSalva = policialRepository.findById(id);
		if(policialSalva.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return policialSalva.get();
	}

	
}
