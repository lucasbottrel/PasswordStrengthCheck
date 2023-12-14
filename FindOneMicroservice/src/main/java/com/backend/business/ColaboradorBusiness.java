package com.backend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.entity.ColaboradorEntity;
import com.backend.repository.ColaboradorRepository;

@Service
public class ColaboradorBusiness {

	@Autowired
	ColaboradorRepository colaboradorRepository;

	public ColaboradorEntity findById(Integer id) {
		return colaboradorRepository.findById(id).get();
	}
}
