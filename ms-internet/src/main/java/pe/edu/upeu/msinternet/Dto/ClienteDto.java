package pe.edu.upeu.msinternet.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Data
// ClienteDto
@Schema(description = "Datos del cliente (identificado por DNI).")
public class ClienteDto {
    @Schema(description = "ID interno", example = "1")
    private Long id;
    @Schema(description = "DNI único del cliente", example = "12345678")
    private String dni;
    @Schema(description = "Nombre y apellidos", example = "Juan Pérez")
    private String nombreCompleto;
    @Schema(description = "Teléfono", example = "987654321")
    private String telefono;
    @Schema(description = "Correo electrónico", example = "juan.perez@example.com")
    private String correo;
    @Schema(description = "Horas acumuladas en el sistema", example = "5")
    private Integer horasAcumuladas;
}
