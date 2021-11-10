package com.gft.crime.api.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.crime.api.entities.Crime;
import com.gft.crime.api.entities.Criminoso;
import com.gft.crime.api.entities.Vitima;
import com.gft.crime.api.repositories.CrimeRepository;
import com.gft.crime.api.repositories.CriminosoRepository;
import com.gft.crime.api.repositories.VitimaRepository;
import com.gft.crime.api.services.exception.CriminosoInexistenteException;
import com.gft.crime.api.services.exception.VitimaInexistenteException;

@Service
public class CrimeService {
	
	@Autowired
	private CrimeRepository crimeRepository;

	@Autowired
	private VitimaRepository vitimaRepository;
	
	@Autowired
	private CriminosoRepository criminosoRepository;
	
	
	public Crime atualizar(Long id, Crime crime) {
		Crime CrimeSalva = buscarVitimaELegistaPeloId(id);
		BeanUtils.copyProperties(crime, CrimeSalva, "id");
		return crimeRepository.save(crime);
	}
	
	
	private Crime buscarVitimaELegistaPeloId(Long id) {
		Optional<Crime> crimeSalva = crimeRepository.findById(id);
		if(crimeSalva.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return crimeSalva.get();
	}

	//TENTA MELHORAR ISSO AQUI DEPOIS, TA FEIO DEMAIS
	public Crime salvar(@Valid Crime crime) {
		Optional<Criminoso> criminoso = criminosoRepository.findById(crime.getCriminoso().getId());
		Optional<Vitima> vitima = vitimaRepository.findById(crime.getVitima().getId());
		if(vitima.isEmpty()) {
			throw new VitimaInexistenteException();
		} else if(criminoso.isEmpty()) {
			throw new CriminosoInexistenteException();
		}
		return crimeRepository.save(crime);
	}

}
