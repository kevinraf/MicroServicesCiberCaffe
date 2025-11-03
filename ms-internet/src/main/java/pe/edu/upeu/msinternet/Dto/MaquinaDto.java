package pe.edu.upeu.msinternet.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "MaquinaDto", description = "Datos expuestos de la máquina del local.")
public class MaquinaDto {

    @Schema(description = "ID interno de la máquina (solo lectura)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Código único de la máquina", example = "PC01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigo;

    @Schema(description = "Nombre visible de la máquina", example = "Máquina 01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(
            description = "Estado operativo de la máquina",
            example = "LIBRE",
            allowableValues = {"LIBRE","OCUPADA","MANTENIMIENTO","BLOQUEADA"}
    )
    private String estado;

    @Schema(description = "Costo por hora de uso (en moneda local)", example = "3.50", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal costoHora;

    @Schema(description = "Descripción o zona", example = "Zona Gamer A")
    private String descripcion;
}
