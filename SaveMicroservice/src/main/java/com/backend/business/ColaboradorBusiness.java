package com.backend.business;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.entity.ColaboradorEntity;
import com.backend.repository.ColaboradorRepository;

@Service
public class ColaboradorBusiness {

	@Autowired
	ColaboradorRepository colaboradorRepository;

	@Autowired
	RabbitTemplate rabbitTemplate;

	public ColaboradorEntity save(ColaboradorEntity colaboradorEntity) throws Exception{
		
		colaboradorEntity = colaboradorRepository.save(colaboradorEntity);
		
		// rabbitTemplate.convertAndSend(BackendApplication.topicExchangeName,
		// "foo.bar.baz",String.valueOf(colaboradorEntity.getId()));

		return colaboradorEntity;
	}
}
