package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entity.ColaboradorEntity;

public interface ColaboradorRepository extends JpaRepository<ColaboradorEntity, Integer>{
	
	
}
