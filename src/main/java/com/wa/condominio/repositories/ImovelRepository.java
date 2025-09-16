package com.wa.condominio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wa.condominio.model.Imovel;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long>{

}
