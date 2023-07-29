package com.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.business.ColaboradorBusiness;
import com.backend.entity.ColaboradorEntity;

@RestController(value = "/colaborador")
public class ColaboradorController {
	
	@Autowired
	ColaboradorBusiness colaboradorBusiness;
	
	@GetMapping("/{id}")
	public ColaboradorEntity get(@PathVariable Integer id) {
		return colaboradorBusiness.findById(id);
	}
	
	@GetMapping
	public List<ColaboradorEntity> get() {
		return colaboradorBusiness.findAll();
	}
	
	@PostMapping
	public ColaboradorEntity post(@RequestBody ColaboradorEntity colaboradorEntity) {
		return colaboradorBusiness.save(colaboradorEntity);
	}
	
}
