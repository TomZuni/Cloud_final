package cl.duoc.cdy2204.msconsumidormensajes.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import cl.duoc.cdy2204.msconsumidormensajes.config.RabbitMQConfig;
import cl.duoc.cdy2204.msconsumidormensajes.dto.ResumenInscripcionDTO;
import cl.duoc.cdy2204.msconsumidormensajes.model.ResumenInscripcion;
import cl.duoc.cdy2204.msconsumidormensajes.repository.ResumenInscripcionRepository;
import cl.duoc.cdy2204.msconsumidormensajes.service.ConsumirMensajeService;

@Service
public class ConsumirMensajeServiceImpl implements ConsumirMensajeService {

	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.username}")
	private String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Value("${spring.rabbitmq.port}")
	private int port;

	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;
	private final ResumenInscripcionRepository resumenInscripcionRepository;

	public ConsumirMensajeServiceImpl(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper,
			ResumenInscripcionRepository resumenInscripcionRepository) {

		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
		this.resumenInscripcionRepository = resumenInscripcionRepository;
	}

	@Override
	public String obtenerUltimoMensaje() {

		String mensaje = null;

		ConnectionFactory factory = new ConnectionFactory();

		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);

		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

			GetResponse response = channel.basicGet("myQueue", true);

			if (response != null) {
				mensaje = new String(response.getBody(), "UTF-8");
				System.out.println("Mensaje recibido: " + mensaje);
			} else {
				System.out.println("No hay mensajes en la cola");
			}

		} catch (Exception e) {
			System.out.println("Error al consumir mensaje de RabbitMQ");
			e.printStackTrace();
		}
		return mensaje;
	}

	// @RabbitListener(queues = RabbitMQConfig.MAIN_QUEUE)
	@Override
	public void recibirMensaje(Message mensaje) {

		System.out.println("Mensaje recibido en myQueue: " + new String(mensaje.getBody()));
	}

	@RabbitListener(queues = RabbitMQConfig.MAIN_QUEUE, ackMode = "MANUAL")
	@Override
	public void recibirMensajeConAckManual(Message mensaje, Channel canal) throws IOException {

		try {
			System.out.println("Mensaje recibido: " + new String(mensaje.getBody()));
			Thread.sleep(10000);

			canal.basicAck(mensaje.getMessageProperties().getDeliveryTag(), false);
			System.out.println("Acknowledge OK enviado");
		} catch (Exception e) {
			canal.basicNack(mensaje.getMessageProperties().getDeliveryTag(), false, false);
			System.out.println("Acknowledge NO OK enviado");
		}
	}

	@Override
	public ResumenInscripcion consumirResumenInscripcion() {

		Message mensaje = rabbitTemplate.receive(RabbitMQConfig.INSCRIPCION_QUEUE, 1000);

		if (mensaje == null) {
			return null;
		}

		try {
			String contenido = new String(mensaje.getBody(), StandardCharsets.UTF_8);
			ResumenInscripcionDTO resumen = objectMapper.readValue(contenido, ResumenInscripcionDTO.class);
			return resumenInscripcionRepository.save(convertirAEntidad(resumen));
		} catch (Exception e) {
			throw new IllegalArgumentException("No fue posible consumir y guardar el resumen de inscripcion", e);
		}
	}

	@Override
	public List<ResumenInscripcion> listarResumenesInscripcion() {

		return resumenInscripcionRepository.findAll();
	}

	private ResumenInscripcion convertirAEntidad(ResumenInscripcionDTO resumen) {

		ResumenInscripcion entidad = new ResumenInscripcion();
		entidad.setRutEstudiante(resumen.getRutEstudiante());
		entidad.setNombreEstudiante(resumen.getNombreEstudiante());
		entidad.setCorreoEstudiante(resumen.getCorreoEstudiante());
		entidad.setCodigoCurso(resumen.getCodigoCurso());
		entidad.setNombreCurso(resumen.getNombreCurso());
		entidad.setModalidad(resumen.getModalidad());
		entidad.setValorCurso(resumen.getValorCurso());
		entidad.setFechaInscripcion(resumen.getFechaInscripcion());
		entidad.setEstado(resumen.getEstado());
		entidad.setFechaRegistro(LocalDateTime.now());
		return entidad;
	}
}
