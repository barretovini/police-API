package com.gft.crime.api.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.crime.api.entities.Criminoso;
import com.gft.crime.api.entities.Delegacia;
import com.gft.crime.api.entities.Delegado;
import com.gft.crime.api.entities.Policial;
import com.gft.crime.api.entities.Prisao;
import com.gft.crime.api.entities.Vitima;
import com.gft.crime.api.repositories.CriminosoRepository;
import com.gft.crime.api.repositories.DelegaciaRepository;
import com.gft.crime.api.repositories.DelegadoRepository;
import com.gft.crime.api.repositories.PolicialRepository;
import com.gft.crime.api.repositories.PrisaoRepository;
import com.gft.crime.api.repositories.VitimaRepository;
import com.gft.crime.api.services.exception.CriminosoInexistenteException;
import com.gft.crime.api.services.exception.DelegaciaInexistenteException;
import com.gft.crime.api.services.exception.DelegadoInexistenteException;
import com.gft.crime.api.services.exception.PolicialInexistenteException;
import com.gft.crime.api.services.exception.VitimaInexistenteException;

@Service
public class PrisaoService {
	
	@Autowired
	private PrisaoRepository prisaoRepository;

	@Autowired
	private PolicialRepository policialRepository;
	
	@Autowired
	private CriminosoRepository criminosoRepository;
	
	@Autowired
	private VitimaRepository vitimaRepository;
	
	@Autowired
	private DelegaciaRepository delegaciaRepository;
	
	@Autowired
	private DelegadoRepository delegadoRepository;
	
	
	public Prisao atualizar(Long id, Prisao prisao) {
		Prisao PrisaoSalva = buscarVitimaELegistaPeloId(id);
		BeanUtils.copyProperties(prisao, PrisaoSalva, "id");
		return prisaoRepository.save(prisao);
	}
	
	
	private Prisao buscarVitimaELegistaPeloId(Long id) {
		Optional<Prisao> prisaoSalva = prisaoRepository.findById(id);
		if(prisaoSalva.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return prisaoSalva.get();
	}

	//TENTA MELHORAR ISSO AQUI DEPOIS, TA FEIO DEMAIS
	public Prisao salvar(@Valid Prisao prisao) {
		Optional<Policial> policial = policialRepository.findById(prisao.getPolicial().getId());
		Optional<Criminoso> criminoso = criminosoRepository.findById(prisao.getCriminoso().getId());
		Optional<Vitima> vitima = vitimaRepository.findById(prisao.getVitima().getId());
		Optional<Delegacia> delegacia = delegaciaRepository.findById(prisao.getDelegacia().getId());
		Optional<Delegado> delegado = delegadoRepository.findById(prisao.getDelegado().getId());
		if(policial.isEmpty()) {
			throw new PolicialInexistenteException();
		} else if(criminoso.isEmpty()) {
			throw new CriminosoInexistenteException();
		} else if(vitima.isEmpty()) {
			throw new VitimaInexistenteException();
		} else if(delegacia.isEmpty()) {
			throw new DelegaciaInexistenteException();
		} else if(delegado.isEmpty()) {
			throw new DelegadoInexistenteException();
		}
		return prisaoRepository.save(prisao);
	}

}

