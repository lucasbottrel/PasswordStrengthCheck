package com.backend.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dto.BossSubordinateDTO;
import com.backend.entity.ColaboradorEntity;
import com.backend.repository.ColaboradorRepository;
import com.backend.utils.Utils;

@Service
public class ColaboradorBusiness {

	@Autowired
	ColaboradorRepository colaboradorRepository;

	public ColaboradorEntity findById(Integer id) {
		return colaboradorRepository.findById(id).get();
	}

	public List<ColaboradorEntity> findAll() {
		return colaboradorRepository.findAll();
	}

	public ColaboradorEntity save(ColaboradorEntity colaboradorEntity) {

		String passwordCheckResult[] = Utils.checkPassword(colaboradorEntity.getSenha()).split(";");

		colaboradorEntity.setComplexidade(passwordCheckResult[0]);
		colaboradorEntity.setScore(passwordCheckResult[1]);

		colaboradorEntity.setSenha(Utils.encrypt(colaboradorEntity.getSenha()));

		return colaboradorRepository.save(colaboradorEntity);
	}

	public ColaboradorEntity associateBoss(BossSubordinateDTO bossSubordinateDTO) {

		ColaboradorEntity boss = colaboradorRepository.findById(bossSubordinateDTO.getIdBoss()).get();
		ColaboradorEntity subordinate = colaboradorRepository.findById(bossSubordinateDTO.getIdSubordinate()).get();

		subordinate.setChefe(boss);

		colaboradorRepository.save(subordinate);

		return subordinate;
	}
}
