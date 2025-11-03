package pe.edu.upeu.mspago.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "PagoDTO", description = "Payload para registrar un pago de una sesión.")
public class PagoDTO {

    @Schema(description = "Código único de la sesión", example = "SES-20251103-0001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigoSesion;

    @Schema(description = "Código de la máquina involucrada", example = "PC01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String maquinaCodigo;

    @Schema(description = "DNI del cliente", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String clienteDni;

    @Schema(description = "Monto total calculado para la sesión", example = "3.38", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal monto;

    @Schema(
            description = "Método de pago (debe existir en el catálogo de métodos).",
            example = "EFECTIVO",
            allowableValues = {"EFECTIVO","YAPE","PLIN","TARJETA","TRANSFERENCIA"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String metodo; // nombre del método (EFECTIVO, YAPE, etc.)
}
