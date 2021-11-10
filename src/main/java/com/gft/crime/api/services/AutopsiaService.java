package com.gft.crime.api.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.crime.api.entities.Autopsia;
import com.gft.crime.api.entities.Legista;
import com.gft.crime.api.entities.Vitima;
import com.gft.crime.api.repositories.AutopsiaRepository;
import com.gft.crime.api.repositories.LegistaRepository;
import com.gft.crime.api.repositories.VitimaRepository;
import com.gft.crime.api.services.exception.LegistaInexistenteException;
import com.gft.crime.api.services.exception.VitimaInexistenteException;

@Service
public class AutopsiaService {
	
	@Autowired
	private AutopsiaRepository autopsiaRepository;

	@Autowired
	private VitimaRepository vitimaRepository;
	
	@Autowired
	private LegistaRepository legistaRepository;
	
	
	public Autopsia atualizar(Long id, Autopsia autopsia) {
		Autopsia AutopsiaSalva = buscarVitimaELegistaPeloId(id);
		BeanUtils.copyProperties(autopsia, AutopsiaSalva, "id");
		return autopsiaRepository.save(autopsia);
	}
	
	
	private Autopsia buscarVitimaELegistaPeloId(Long id) {
		Optional<Autopsia> autopsiaSalva = autopsiaRepository.findById(id);
		if(autopsiaSalva.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return autopsiaSalva.get();
	}

	//TENTA MELHORAR ISSO AQUI DEPOIS, TA FEIO DEMAIS
	public Autopsia salvar(@Valid Autopsia autopsia) {
		Optional<Legista> legista = legistaRepository.findById(autopsia.getLegista().getId());
		Optional<Vitima> vitima = vitimaRepository.findById(autopsia.getVitima().getId());
		if(vitima.isEmpty()) {
			throw new VitimaInexistenteException();
		} else if(legista.isEmpty()) {
			throw new LegistaInexistenteException();
		}
		return autopsiaRepository.save(autopsia);
	}

}
