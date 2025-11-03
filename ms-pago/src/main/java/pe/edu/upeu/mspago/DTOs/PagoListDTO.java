package pe.edu.upeu.mspago.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(name = "PagoListDTO", description = "Vista de listado/detalle de pagos.")
public class PagoListDTO {

    @Schema(description = "ID interno del pago", example = "1")
    private Long id;

    @Schema(description = "Código de la sesión pagada", example = "SES-SEED-001")
    private String codigoSesion;

    @Schema(description = "Código de máquina asociado", example = "PC02")
    private String maquinaCodigo;

    @Schema(description = "DNI del cliente", example = "87654321")
    private String clienteDni;

    @Schema(description = "Monto del pago", example = "3.75")
    private BigDecimal monto;

    @Schema(description = "Nombre del método de pago", example = "EFECTIVO")
    private String metodoPago;

    @Schema(
            description = "Estado del pago",
            example = "PAGADO",
            allowableValues = {"PENDIENTE","PAGADO","ANULADO"}
    )
    private String estado;

    @Schema(description = "Fecha de registro/confirmación del pago", example = "2025-11-03T09:00:00")
    private LocalDateTime fechaPago;

    @Schema(description = "Observación adicional", example = "Pendiente de confirmar (YAPE).")
    private String observacion;
}
