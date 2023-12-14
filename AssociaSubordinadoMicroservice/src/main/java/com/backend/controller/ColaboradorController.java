package com.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.business.ColaboradorBusiness;
import com.backend.dto.BossSubordinateDTO;
import com.backend.entity.ColaboradorEntity;

@RestController(value = "/colaborador")
public class ColaboradorController {
	
	@Autowired
	ColaboradorBusiness colaboradorBusiness;
	
	@PostMapping(path = "/associateBoss")
	public ColaboradorEntity associateBoss(@RequestBody BossSubordinateDTO bossSubordinateDTO) {
		return colaboradorBusiness.associateBoss(bossSubordinateDTO);
	}
	
}
