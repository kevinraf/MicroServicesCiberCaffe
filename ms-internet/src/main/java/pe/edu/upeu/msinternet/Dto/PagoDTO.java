package pe.edu.upeu.msinternet.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/** DTO para Feign hacia ms-pago */
@Data
@Schema(name = "PagoDTO", description = "Solicitud de registro de pago hacia ms-pago.")
public class PagoDTO {

    @Schema(description = "Código de la sesión a pagar", example = "SES-20251103-0001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigoSesion;

    @Schema(description = "Código de la máquina utilizada", example = "PC01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String maquinaCodigo;

    @Schema(description = "DNI del cliente", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String clienteDni;

    @Schema(description = "Monto total calculado para la sesión", example = "3.38", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal monto;

    @Schema(
            description = "Método de pago (debe existir en ms-pago.metodos-pago)",
            example = "EFECTIVO",
            allowableValues = {"EFECTIVO","YAPE","PLIN","TARJETA","TRANSFERENCIA"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String metodo; // EFECTIVO/YAPE/...
}
