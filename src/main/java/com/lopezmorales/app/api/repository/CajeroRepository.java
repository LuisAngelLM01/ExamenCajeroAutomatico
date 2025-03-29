package com.lopezmorales.app.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lopezmorales.app.api.model.Cajero;

@Repository
public interface CajeroRepository extends JpaRepository<Cajero, Long>{
}
