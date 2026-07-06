package cl.duoc.cdy2204.msproductormensajes.service;

import cl.duoc.cdy2204.msproductormensajes.dto.ResumenInscripcionDTO;

public interface ProducirMensajeService {

	void enviarMensaje(String mensaje);

	void enviarObjeto(Object objeto);

	void enviarResumenInscripcion(ResumenInscripcionDTO resumenInscripcion);
}
