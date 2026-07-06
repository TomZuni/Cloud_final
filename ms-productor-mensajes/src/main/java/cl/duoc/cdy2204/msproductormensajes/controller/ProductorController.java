package cl.duoc.cdy2204.msproductormensajes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.cdy2204.msproductormensajes.dto.ProductoDTO;
import cl.duoc.cdy2204.msproductormensajes.dto.ResumenInscripcionDTO;
import cl.duoc.cdy2204.msproductormensajes.dto.UsuarioDTO;
import cl.duoc.cdy2204.msproductormensajes.service.ProducirMensajeService;

@RestController
@RequestMapping("/api")
public class ProductorController {

	private final ProducirMensajeService producirMensajeService;

	public ProductorController(ProducirMensajeService producirMensajeService) {

		this.producirMensajeService = producirMensajeService;
	}

	@PostMapping("/mensajes")
	public ResponseEntity<String> enviar(@RequestBody String mensaje) {

		producirMensajeService.enviarMensaje(mensaje);
		return ResponseEntity.ok("Mensaje enviado: " + mensaje);
	}

	@PostMapping("/usuarios")
	public ResponseEntity<String> enviarObjetoUsuario(@RequestBody UsuarioDTO usuario) {

		producirMensajeService.enviarObjeto(usuario);
		return ResponseEntity.ok("Mensaje enviado: " + usuario.toString());
	}

	@PostMapping("/productos")
	public ResponseEntity<String> enviarObjetoProducto(@RequestBody ProductoDTO producto) {

		producirMensajeService.enviarObjeto(producto);
		return ResponseEntity.ok("Mensaje enviado: " + producto.toString());
	}

	@PostMapping("/inscripciones/resumen")
	public ResponseEntity<String> enviarResumenInscripcion(@RequestBody ResumenInscripcionDTO resumenInscripcion) {

		producirMensajeService.enviarResumenInscripcion(resumenInscripcion);
		return ResponseEntity.ok("Resumen de inscripcion enviado a RabbitMQ: " + resumenInscripcion.toString());
	}
}
