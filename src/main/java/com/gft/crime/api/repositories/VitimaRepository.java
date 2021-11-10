package com.gft.crime.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.crime.api.entities.Vitima;

@Repository
public interface VitimaRepository extends JpaRepository<Vitima, Long> {

}
