package com.rabbitmqconsumer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rabbitmqconsumer.entity.ColaboradorEntity;

public interface ColaboradorRepository extends JpaRepository<ColaboradorEntity, Integer>{
	
	
}
