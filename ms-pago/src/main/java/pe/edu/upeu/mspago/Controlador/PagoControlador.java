package pe.edu.upeu.mspago.Controlador;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.mspago.DTOs.PagoDTO;
import pe.edu.upeu.mspago.DTOs.PagoListDTO;
import pe.edu.upeu.mspago.DTOs.PagoRespDTO;
import pe.edu.upeu.mspago.Servicio.PagoServicio;

import java.util.List;

@RestController
@RequestMapping("/pagos")
@Tag(name = "Pagos", description = "Gestión de pagos de sesiones (registro, confirmación y consulta).")
@RequiredArgsConstructor
public class PagoControlador {

    private final PagoServicio servicio;

    @Operation(
            summary = "Registrar un pago",
            description = "Registra un pago para una sesión específica. **Debe** existir el método de pago (ver `/metodos-pago`). " +
                    "Este endpoint lo invoca ms-internet cuando finalizas una sesión.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = PagoDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Pago en EFECTIVO",
                                            value = "{\n" +
                                                    "  \"codigoSesion\": \"SES-20251103-0001\",\n" +
                                                    "  \"maquinaCodigo\": \"PC01\",\n" +
                                                    "  \"clienteDni\": \"12345678\",\n" +
                                                    "  \"monto\": 3.38,\n" +
                                                    "  \"metodo\": \"EFECTIVO\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Pago con YAPE",
                                            value = "{\n" +
                                                    "  \"codigoSesion\": \"SES-20251103-0002\",\n" +
                                                    "  \"maquinaCodigo\": \"PC02\",\n" +
                                                    "  \"clienteDni\": \"87654321\",\n" +
                                                    "  \"monto\": 5.50,\n" +
                                                    "  \"metodo\": \"YAPE\"\n" +
                                                    "}"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pago registrado",
                            content = @Content(schema = @Schema(implementation = PagoRespDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Respuesta OK",
                                                    value = "{ \"id\": 10, \"codigoSesion\": \"SES-20251103-0001\", \"estado\": \"PENDIENTE\" }"
                                            )
                                    }
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<PagoRespDTO> registrar(@RequestBody PagoDTO dto){
        return ResponseEntity.ok(servicio.registrar(dto));
    }

    @Operation(
            summary = "Confirmar pago",
            description = "Cambia el estado del pago a **PAGADO** (por ejemplo, tras validar el depósito/Yape). " +
                    "El ID del pago se obtiene de la respuesta de registro o del listado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pago confirmado",
                            content = @Content(schema = @Schema(implementation = PagoRespDTO.class),
                                    examples = @ExampleObject(
                                            name = "Confirmado",
                                            value = "{ \"id\": 10, \"codigoSesion\": \"SES-20251103-0001\", \"estado\": \"PAGADO\" }"
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<PagoRespDTO> confirmar(@PathVariable Long id){
        return ResponseEntity.ok(servicio.confirmar(id));
    }

    @Operation(
            summary = "Listar pagos",
            description = "Listado de pagos con detalle (método, estado, fecha, observación).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PagoListDTO.class)),
                                    examples = @ExampleObject(
                                            name = "Ejemplo listado",
                                            value = "[\n" +
                                                    "  {\n" +
                                                    "    \"id\": 1,\n" +
                                                    "    \"codigoSesion\": \"SES-SEED-001\",\n" +
                                                    "    \"maquinaCodigo\": \"PC02\",\n" +
                                                    "    \"clienteDni\": \"87654321\",\n" +
                                                    "    \"monto\": 3.75,\n" +
                                                    "    \"metodoPago\": \"EFECTIVO\",\n" +
                                                    "    \"estado\": \"PAGADO\",\n" +
                                                    "    \"fechaPago\": \"2025-11-03T08:55:21\",\n" +
                                                    "    \"observacion\": \"Pago semilla (EFECTIVO).\"\n" +
                                                    "  },\n" +
                                                    "  {\n" +
                                                    "    \"id\": 2,\n" +
                                                    "    \"codigoSesion\": \"SES-SEED-002\",\n" +
                                                    "    \"maquinaCodigo\": \"PC01\",\n" +
                                                    "    \"clienteDni\": \"12345678\",\n" +
                                                    "    \"monto\": 1.46,\n" +
                                                    "    \"metodoPago\": \"YAPE\",\n" +
                                                    "    \"estado\": \"PENDIENTE\",\n" +
                                                    "    \"fechaPago\": \"2025-11-03T09:00:00\",\n" +
                                                    "    \"observacion\": \"Pendiente de confirmar (YAPE).\"\n" +
                                                    "  }\n" +
                                                    "]"
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<PagoListDTO>> listar(){
        return ResponseEntity.ok(servicio.listar());
    }
}
