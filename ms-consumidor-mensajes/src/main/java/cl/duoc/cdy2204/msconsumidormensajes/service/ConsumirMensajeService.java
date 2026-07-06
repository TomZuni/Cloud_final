package cl.duoc.cdy2204.msconsumidormensajes.service;

import java.io.IOException;
import java.util.List;

import org.springframework.amqp.core.Message;

import com.rabbitmq.client.Channel;

import cl.duoc.cdy2204.msconsumidormensajes.model.ResumenInscripcion;

public interface ConsumirMensajeService {

	void recibirMensaje(Message mensaje);

	void recibirMensajeConAckManual(Message mensaje, Channel canal) throws IOException;

	String obtenerUltimoMensaje();

	ResumenInscripcion consumirResumenInscripcion();

	List<ResumenInscripcion> listarResumenesInscripcion();
}
