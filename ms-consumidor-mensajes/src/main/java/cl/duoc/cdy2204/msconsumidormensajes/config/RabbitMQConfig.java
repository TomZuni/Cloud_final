package cl.duoc.cdy2204.msconsumidormensajes.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String MAIN_QUEUE = "myQueue";
	public static final String MAIN_EXCHANGE = "myExchange";
	public static final String INSCRIPCION_QUEUE = "resumenInscripcionQueue";
	public static final String INSCRIPCION_EXCHANGE = "resumenInscripcionExchange";
	public static final String INSCRIPCION_ROUTING_KEY = "resumen.inscripcion";

	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.port}")
	private int port;

	@Value("${spring.rabbitmq.username}")
	private String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Bean
	Jackson2JsonMessageConverter messageConverter() {

		return new Jackson2JsonMessageConverter();
	}

	@Bean
	CachingConnectionFactory connectionFactory() {

		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);
		return factory;
	}

	@Bean
	Queue myQueue() {

		return new Queue(MAIN_QUEUE, true, false, false, null);
	}

	@Bean
	DirectExchange myExchange() {

		return new DirectExchange(MAIN_EXCHANGE);
	}

	@Bean
	Binding binding(Queue myQueue, DirectExchange myExchange) {

		return BindingBuilder.bind(myQueue).to(myExchange).with("");
	}

	@Bean
	Queue inscripcionQueue() {

		return new Queue(INSCRIPCION_QUEUE, true, false, false, null);
	}

	@Bean
	DirectExchange inscripcionExchange() {

		return new DirectExchange(INSCRIPCION_EXCHANGE);
	}

	@Bean
	Binding inscripcionBinding(Queue inscripcionQueue, DirectExchange inscripcionExchange) {

		return BindingBuilder.bind(inscripcionQueue).to(inscripcionExchange).with(INSCRIPCION_ROUTING_KEY);
	}
}
