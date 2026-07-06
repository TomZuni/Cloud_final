package cl.duoc.cdy2204.msproductormensajes.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResumenInscripcionDTO {

	private String rutEstudiante;
	private String nombreEstudiante;
	private String correoEstudiante;
	private String codigoCurso;
	private String nombreCurso;
	private String modalidad;
	private BigDecimal valorCurso;
	private LocalDate fechaInscripcion;
	private String estado;
}
