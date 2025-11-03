package pe.edu.upeu.msinternet.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "SesionDto", description = "Respuesta con el estado actual de la sesión.")
public class SesionDto {

    @Schema(description = "ID interno de la sesión (solo lectura)", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Código único de la sesión", example = "SES-20251103-0001")
    private String codigo;

    @Schema(description = "Código de la máquina usada", example = "PC01")
    private String maquinaCodigo;

    @Schema(description = "DNI del cliente", example = "12345678")
    private String clienteDni;

    @Schema(description = "Minutos asignados originalmente", example = "60")
    private Integer minutosAsignados;

    @Schema(description = "Minutos consumidos hasta el momento", example = "58")
    private Integer minutosConsumidos;

    @Schema(
            description = "Estado de la sesión",
            example = "EN_CURSO",
            allowableValues = {"EN_CURSO","FINALIZADA","CANCELADA"}
    )
    private String estado;

    @Schema(description = "Costo por hora aplicado en el inicio de la sesión", example = "3.50")
    private BigDecimal costoHora;

    @Schema(description = "Total calculado proporcional a los minutos consumidos", example = "3.38")
    private BigDecimal totalCalculado;
}
