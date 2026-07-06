package cl.duoc.cdy2204.msproductormensajes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoDTO {

	private Long id;
	private String nombre;
	private String categoria;
}
