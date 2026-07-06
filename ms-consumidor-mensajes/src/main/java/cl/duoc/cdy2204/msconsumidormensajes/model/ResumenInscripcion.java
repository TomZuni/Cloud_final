package cl.duoc.cdy2204.msconsumidormensajes.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resumen_inscripciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumenInscripcion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 20)
	private String rutEstudiante;

	@Column(nullable = false, length = 120)
	private String nombreEstudiante;

	@Column(length = 120)
	private String correoEstudiante;

	@Column(nullable = false, length = 30)
	private String codigoCurso;

	@Column(nullable = false, length = 120)
	private String nombreCurso;

	@Column(length = 40)
	private String modalidad;

	@Column(precision = 12, scale = 2)
	private BigDecimal valorCurso;

	private LocalDate fechaInscripcion;

	@Column(length = 30)
	private String estado;

	@Column(nullable = false)
	private LocalDateTime fechaRegistro;
}
