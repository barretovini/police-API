package com.gft.crime.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.crime.api.entities.Policial;

@Repository
public interface PolicialRepository extends JpaRepository<Policial, Long> {

	Page<Policial> findByNome(String nome, Pageable pageable);
	

}
