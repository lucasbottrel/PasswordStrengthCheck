package com.rabbitmqconsumer.receiver;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmqconsumer.entity.ColaboradorEntity;
import com.backend.utils.Utils;
import com.rabbitmqconsumer.ColaboradorRepository;

@Component
public class Receiver {

  private CountDownLatch latch = new CountDownLatch(1);
  
	@Autowired
	ColaboradorRepository colaboradorRepository;

  public void receiveMessage(String message) {
    System.out.println("Received <" + message + ">");
    
    ColaboradorEntity colaboradorEntity = colaboradorRepository.findById(Integer.valueOf(message)).get();
    
	String passwordCheckResult[] = Utils.checkPassword(colaboradorEntity.getSenha()).split(";");
    
    colaboradorEntity.setComplexidade(passwordCheckResult[0]);
	colaboradorEntity.setScore(passwordCheckResult[1]);

	colaboradorEntity.setSenha(Utils.encrypt(colaboradorEntity.getSenha()));
	
	colaboradorEntity = colaboradorRepository.save(colaboradorEntity);
    
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }

}