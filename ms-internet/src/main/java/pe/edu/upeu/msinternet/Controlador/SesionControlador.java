package pe.edu.upeu.msinternet.Controlador;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.msinternet.Dto.SesionCrearDto;
import pe.edu.upeu.msinternet.Dto.SesionDto;
import pe.edu.upeu.msinternet.Servicio.SesionServicio;

@RestController
@RequestMapping("/sesiones")
@RequiredArgsConstructor
@Tag(
        name = "Sesiones",
        description = "Ciclo de uso por cliente en una máquina. "
                + "Estados: EN_CURSO, FINALIZADA, CANCELADA. "
                + "Al finalizar, se calcula el total y se integra con ms-pago para registrar/confirmar el pago."
)
@SecurityRequirement(name = "bearerAuth")
public class SesionControlador {
    private final SesionServicio servicio;

    @Operation(
            summary = "Iniciar sesión",
            description = "Inicia una sesión para un cliente en una máquina, asignando minutos."
                    + "<br><b>Lógica de negocio:</b> valida que la máquina esté LIBRE y el cliente exista. "
                    + "La máquina pasa a OCUPADA y se registra el costo/hora de ese momento.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = SesionCrearDto.class),
                            examples = @ExampleObject(name = "IniciarSesion",
                                    value = "{\n" +
                                            "  \"codigoMaquina\": \"PC01\",\n" +
                                            "  \"dniCliente\": \"12345678\",\n" +
                                            "  \"minutosAsignados\": 60\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sesión iniciada",
                            content = @Content(schema = @Schema(implementation = SesionDto.class),
                                    examples = @ExampleObject(name = "SesionEnCurso",
                                            value = "{\n" +
                                                    "  \"id\": 10,\n" +
                                                    "  \"codigo\": \"SES-20251103-0001\",\n" +
                                                    "  \"maquinaCodigo\": \"PC01\",\n" +
                                                    "  \"clienteDni\": \"12345678\",\n" +
                                                    "  \"minutosAsignados\": 60,\n" +
                                                    "  \"minutosConsumidos\": 0,\n" +
                                                    "  \"estado\": \"EN_CURSO\",\n" +
                                                    "  \"costoHora\": 3.50,\n" +
                                                    "  \"totalCalculado\": 0.00\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Máquina no disponible o datos inválidos"),
                    @ApiResponse(responseCode = "404", description = "Cliente/Máquina no existe")
            }
    )
    @PostMapping("/iniciar")
    public ResponseEntity<SesionDto> iniciar(@RequestBody SesionCrearDto dto){
        return ResponseEntity.ok(servicio.iniciar(dto));
    }

    @Operation(
            summary = "Finalizar sesión",
            description = "Cierra la sesión, calcula el consumo total y dispara el registro de pago en ms-pago."
                    + "<br><b>Lógica de negocio:</b> la máquina se libera; se registra el total proporcional a minutos "
                    + "consumidos y se manda a ms-pago el pago PENDIENTE con el método elegido.",
            parameters = {
                    @Parameter(name = "codigo", description = "Código de sesión", example = "SES-20251103-0001"),
                    @Parameter(name = "metodoPago", description = "Método de pago (coincide con ms-pago.metodos-pago.nombre)", example = "EFECTIVO")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sesión finalizada",
                            content = @Content(schema = @Schema(implementation = SesionDto.class),
                                    examples = @ExampleObject(name = "SesionFinalizada",
                                            value = "{\n" +
                                                    "  \"id\": 10,\n" +
                                                    "  \"codigo\": \"SES-20251103-0001\",\n" +
                                                    "  \"maquinaCodigo\": \"PC01\",\n" +
                                                    "  \"clienteDni\": \"12345678\",\n" +
                                                    "  \"minutosAsignados\": 60,\n" +
                                                    "  \"minutosConsumidos\": 58,\n" +
                                                    "  \"estado\": \"FINALIZADA\",\n" +
                                                    "  \"costoHora\": 3.50,\n" +
                                                    "  \"totalCalculado\": 3.38\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "La sesión no está en curso"),
                    @ApiResponse(responseCode = "404", description = "Sesión no encontrada")
            }
    )
    @PutMapping("/{codigo}/finalizar")
    public ResponseEntity<SesionDto> finalizar(
            @PathVariable String codigo,
            @RequestParam String metodoPago){
        return ResponseEntity.ok(servicio.finalizar(codigo, metodoPago));
    }

    @Operation(
            summary = "Cancelar sesión",
            description = "Cancela una sesión en curso (sin costo). "
                    + "<br><b>Lógica de negocio:</b> libera la máquina a LIBRE y no registra pago.",
            parameters = @Parameter(name = "codigo", description = "Código de sesión", example = "SES-20251103-0002"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sesión cancelada",
                            content = @Content(schema = @Schema(implementation = SesionDto.class),
                                    examples = @ExampleObject(name = "SesionCancelada",
                                            value = "{\n" +
                                                    "  \"id\": 11,\n" +
                                                    "  \"codigo\": \"SES-20251103-0002\",\n" +
                                                    "  \"maquinaCodigo\": \"PC02\",\n" +
                                                    "  \"clienteDni\": \"87654321\",\n" +
                                                    "  \"minutosAsignados\": 30,\n" +
                                                    "  \"minutosConsumidos\": 5,\n" +
                                                    "  \"estado\": \"CANCELADA\",\n" +
                                                    "  \"costoHora\": 2.50,\n" +
                                                    "  \"totalCalculado\": 0.21\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "La sesión no está en curso"),
                    @ApiResponse(responseCode = "404", description = "Sesión no encontrada")
            }
    )
    @PutMapping("/{codigo}/cancelar")
    public ResponseEntity<SesionDto> cancelar(@PathVariable String codigo){
        return ResponseEntity.ok(servicio.cancelar(codigo));
    }
}
