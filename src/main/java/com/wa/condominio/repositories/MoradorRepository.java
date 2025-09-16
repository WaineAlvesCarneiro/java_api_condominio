package com.wa.condominio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wa.condominio.model.Morador;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long> {

}