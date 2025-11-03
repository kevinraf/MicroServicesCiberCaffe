package pe.edu.upeu.mspago.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "PagoRespDTO", description = "Respuesta mínima tras registrar/confirmar un pago.")
public class PagoRespDTO {

    @Schema(description = "ID del pago creado/actualizado", example = "10")
    private Long id;

    @Schema(description = "Código de sesión asociado", example = "SES-20251103-0001")
    private String codigoSesion;

    @Schema(
            description = "Estado actual del pago",
            example = "PENDIENTE",
            allowableValues = {"PENDIENTE","PAGADO","ANULADO"}
    )
    private String estado; // PENDIENTE/PAGADO/ANULADO
}
