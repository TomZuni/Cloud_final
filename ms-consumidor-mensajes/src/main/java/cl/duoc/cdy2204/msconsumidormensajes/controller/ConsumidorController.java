package cl.duoc.cdy2204.msconsumidormensajes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.cdy2204.msconsumidormensajes.model.ResumenInscripcion;
import cl.duoc.cdy2204.msconsumidormensajes.service.ConsumirMensajeService;

@RestController
@RequestMapping("/api")
public class ConsumidorController {

	private final ConsumirMensajeService consumirMensajeService;

	public ConsumidorController(ConsumirMensajeService consumirMensajeService) {

		this.consumirMensajeService = consumirMensajeService;
	}

	@GetMapping("/mensaje/ultimo")
	public ResponseEntity<String> obtenerUltimoMensaje() {

		String message = consumirMensajeService.obtenerUltimoMensaje();

		if (null == message) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(message);
		}
	}

	@PostMapping("/inscripciones/consumir")
	public ResponseEntity<ResumenInscripcion> consumirResumenInscripcion() {

		ResumenInscripcion resumenGuardado = consumirMensajeService.consumirResumenInscripcion();

		if (resumenGuardado == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(resumenGuardado);
	}

	@GetMapping("/inscripciones/resumenes")
	public ResponseEntity<List<ResumenInscripcion>> listarResumenesInscripcion() {

		return ResponseEntity.ok(consumirMensajeService.listarResumenesInscripcion());
	}
}
