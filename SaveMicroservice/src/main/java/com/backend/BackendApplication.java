package com.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendApplication {

	public static final String topicExchangeName = "spring-boot-exchange";
	public static final String queueName = "spring-boot";

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedHeaders("*");
			}
		};
	}

	/*
	 * @Bean Queue queue() { return new Queue(queueName, false); }
	 * 
	 * @Bean TopicExchange exchange() { return new TopicExchange(topicExchangeName);
	 * }
	 * 
	 * @Bean Binding binding(Queue queue, TopicExchange exchange) { return
	 * BindingBuilder.bind(queue).to(exchange).with("foo.bar.#"); }
	 * 
	 * @Bean SimpleMessageListenerContainer container(ConnectionFactory
	 * connectionFactory) { SimpleMessageListenerContainer container = new
	 * SimpleMessageListenerContainer();
	 * container.setConnectionFactory(connectionFactory);
	 * container.setQueueNames(queueName); return container; }
	 */
}
