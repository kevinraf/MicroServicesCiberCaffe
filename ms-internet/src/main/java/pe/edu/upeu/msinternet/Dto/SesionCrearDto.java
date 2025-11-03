package pe.edu.upeu.msinternet.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "SesionCrearDto", description = "Payload para iniciar una sesión de uso.")
public class SesionCrearDto {

    @Schema(description = "Código de la máquina donde se usará", example = "PC01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigoMaquina;

    @Schema(description = "DNI del cliente que usará la máquina", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dniCliente;

    @Schema(description = "Minutos asignados para la sesión", example = "60", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer minutosAsignados;
}
