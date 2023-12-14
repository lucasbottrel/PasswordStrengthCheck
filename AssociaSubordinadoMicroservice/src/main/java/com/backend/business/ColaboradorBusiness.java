package com.backend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dto.BossSubordinateDTO;
import com.backend.entity.ColaboradorEntity;
import com.backend.repository.ColaboradorRepository;

@Service
public class ColaboradorBusiness {

	@Autowired
	ColaboradorRepository colaboradorRepository;

	public ColaboradorEntity associateBoss(BossSubordinateDTO bossSubordinateDTO) {

		ColaboradorEntity subordinate = colaboradorRepository.findById(bossSubordinateDTO.getIdSubordinate()).get();
		ColaboradorEntity boss = colaboradorRepository.findById(bossSubordinateDTO.getIdBoss()).get();
		
		if (!subordinate.getId().equals(boss.getId())) {

			subordinate.setChefe(boss);

			colaboradorRepository.save(subordinate);
		}

		return subordinate;
	}
}
