package com.gft.crime.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.crime.api.entities.Prisao;

@Repository
public interface PrisaoRepository extends JpaRepository<Prisao, Long> {

}
