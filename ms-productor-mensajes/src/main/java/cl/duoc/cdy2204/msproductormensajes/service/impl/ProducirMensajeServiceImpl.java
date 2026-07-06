package cl.duoc.cdy2204.msproductormensajes.service.impl;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.cdy2204.msproductormensajes.config.RabbitMQConfig;
import cl.duoc.cdy2204.msproductormensajes.dto.ResumenInscripcionDTO;
import cl.duoc.cdy2204.msproductormensajes.service.ProducirMensajeService;

@Service
public class ProducirMensajeServiceImpl implements ProducirMensajeService {

	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;

	public ProducirMensajeServiceImpl(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
	}

	@Override
	public void enviarMensaje(String mensaje) {

		rabbitTemplate.convertAndSend(RabbitMQConfig.MAIN_QUEUE, mensaje);
	}

	@Override
	public void enviarObjeto(Object objeto) {

		rabbitTemplate.convertAndSend(RabbitMQConfig.MAIN_QUEUE, objeto);
	}

	@Override
	public void enviarResumenInscripcion(ResumenInscripcionDTO resumenInscripcion) {

		try {
			String json = objectMapper.writeValueAsString(resumenInscripcion);
			Message mensaje = MessageBuilder.withBody(json.getBytes(StandardCharsets.UTF_8))
					.setContentType(MessageProperties.CONTENT_TYPE_JSON).build();

			rabbitTemplate.send(RabbitMQConfig.INSCRIPCION_EXCHANGE, RabbitMQConfig.INSCRIPCION_ROUTING_KEY, mensaje);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("No fue posible convertir el resumen de inscripcion a JSON", e);
		}
	}
}
