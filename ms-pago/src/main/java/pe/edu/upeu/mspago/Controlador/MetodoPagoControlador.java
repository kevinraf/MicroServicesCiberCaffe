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
import pe.edu.upeu.mspago.Entidad.MetodoPago;
import pe.edu.upeu.mspago.Servicio.MetodoPagoServicio;

import java.util.List;

@RestController
@RequestMapping("/metodos-pago")
@RequiredArgsConstructor
@Tag(name = "Métodos de Pago", description = "Catálogo de métodos de pago disponibles (EFECTIVO, YAPE, PLIN, etc.)")
public class MetodoPagoControlador {

    private final MetodoPagoServicio servicio;

    @Operation(
            summary = "Crear método de pago",
            description = "Registra un nuevo método de pago en el catálogo. Normalmente se carga por seeder, pero queda habilitado para mantenimiento.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = MetodoPago.class),
                            examples = {
                                    @ExampleObject(
                                            name = "EFECTIVO",
                                            value = "{ \"nombre\": \"EFECTIVO\", \"descripcion\": \"Pago en efectivo\", \"activo\": true }"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Creado correctamente",
                            content = @Content(schema = @Schema(implementation = MetodoPago.class)))
            }
    )
    @PostMapping
    public ResponseEntity<MetodoPago> crear(@RequestBody MetodoPago mp){
        return ResponseEntity.ok(servicio.crear(mp));
    }

    @Operation(
            summary = "Listar métodos de pago",
            description = "Devuelve todos los métodos de pago habilitados/registrados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MetodoPago.class)),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Listado demo",
                                                    value = "[\n" +
                                                            "  {\"id\":1, \"nombre\":\"EFECTIVO\", \"descripcion\":\"Pago en efectivo\", \"activo\":true},\n" +
                                                            "  {\"id\":2, \"nombre\":\"YAPE\", \"descripcion\":\"Pago con Yape\", \"activo\":true}\n" +
                                                            "]"
                                            )
                                    }
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<MetodoPago>> listar(){
        return ResponseEntity.ok(servicio.listar());
    }
}
