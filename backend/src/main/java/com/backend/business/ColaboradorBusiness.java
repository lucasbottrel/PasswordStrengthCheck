package com.backend.business;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.sound.midi.Receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.backend.BackendApplication;
import com.backend.dto.BossSubordinateDTO;
import com.backend.entity.ColaboradorEntity;
import com.backend.repository.ColaboradorRepository;
import com.backend.utils.Utils;

@Service
public class ColaboradorBusiness {

	@Autowired
	ColaboradorRepository colaboradorRepository;

//	@Autowired
//	RabbitTemplate rabbitTemplate;

	public ColaboradorEntity findById(Integer id) {
		return colaboradorRepository.findById(id).get();
	}

	public List<ColaboradorEntity> findAll() {
		return colaboradorRepository.findAll();
	}

	public ColaboradorEntity save(ColaboradorEntity colaboradorEntity) throws Exception{
		
		colaboradorEntity = colaboradorRepository.save(colaboradorEntity);
	    
		String passwordCheckResult[] = Utils.checkPassword(colaboradorEntity.getSenha()).split(";");
	    
	    colaboradorEntity.setComplexidade(passwordCheckResult[0]);
		colaboradorEntity.setScore(passwordCheckResult[1]);

		colaboradorEntity.setSenha(Utils.encrypt(colaboradorEntity.getSenha()));
		
		colaboradorEntity = colaboradorRepository.save(colaboradorEntity);

		return colaboradorEntity;
	}

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
