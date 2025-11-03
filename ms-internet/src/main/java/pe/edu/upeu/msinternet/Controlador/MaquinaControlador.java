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
import pe.edu.upeu.msinternet.Dto.MaquinaDto;
import pe.edu.upeu.msinternet.Servicio.MaquinaServicio;

import java.util.List;

@RestController
@RequestMapping("/maquinas")
@RequiredArgsConstructor
@Tag(
        name = "Máquinas",
        description = "Administración de PCs locales. El estado puede ser LIBRE/OCUPADA/MANTENIMIENTO/BLOQUEADA. " +
                "El costo por hora se define por máquina."
)
@SecurityRequirement(name = "bearerAuth")
public class MaquinaControlador {
    private final MaquinaServicio servicio;

    @Operation(
            summary = "Crear máquina",
            description = "Registra una nueva máquina del local."
                    + "<br><b>Lógica de negocio:</b> una máquina LIBRE puede iniciar sesiones; si se BLOQUEA no puede usarse.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = MaquinaDto.class),
                            examples = @ExampleObject(name = "NuevaPC",
                                    value = "{\n" +
                                            "  \"codigo\": \"PC10\",\n" +
                                            "  \"nombre\": \"Máquina 10\",\n" +
                                            "  \"estado\": \"LIBRE\",\n" +
                                            "  \"costoHora\": 3.50,\n" +
                                            "  \"descripcion\": \"Zona Gamer\"\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Máquina creada",
                            content = @Content(schema = @Schema(implementation = MaquinaDto.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping
    public ResponseEntity<MaquinaDto> crear(@RequestBody MaquinaDto dto){
        return ResponseEntity.ok(servicio.crear(dto));
    }

    @Operation(
            summary = "Listar máquinas",
            description = "Retorna todas las máquinas registradas con su estado actual."
    )
    @GetMapping
    public ResponseEntity<List<MaquinaDto>> listar(){
        return ResponseEntity.ok(servicio.listar());
    }

    @Operation(
            summary = "Buscar máquina por código",
            description = "Devuelve la máquina por su código único (por ejemplo PC01).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = MaquinaDto.class),
                                    examples = @ExampleObject(name = "PC01",
                                            value = "{\n" +
                                                    "  \"id\": 1,\n" +
                                                    "  \"codigo\": \"PC01\",\n" +
                                                    "  \"nombre\": \"Máquina 01\",\n" +
                                                    "  \"estado\": \"OCUPADA\",\n" +
                                                    "  \"costoHora\": 3.50,\n" +
                                                    "  \"descripcion\": \"Zona Gamer\"\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "No existe máquina con ese código")
            }
    )
    @GetMapping("/{codigo}")
    public ResponseEntity<MaquinaDto> buscar(
            @Parameter(description = "Código único de la máquina", example = "PC01")
            @PathVariable String codigo){
        return ResponseEntity.ok(servicio.buscarPorCodigo(codigo));
    }

    @Operation(
            summary = "Bloquear máquina",
            description = "Cambia el estado a BLOQUEADA. "
                    + "<br><b>Lógica de negocio:</b> una máquina bloqueada no puede iniciar nuevas sesiones.",
            responses = @ApiResponse(responseCode = "200", description = "Bloqueada")
    )
    @PutMapping("/{codigo}/bloquear")
    public ResponseEntity<Void> bloquear(
            @Parameter(description = "Código de la máquina", example = "PC02")
            @PathVariable String codigo){
        servicio.bloquear(codigo);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Liberar máquina",
            description = "Cambia el estado a LIBRE para que pueda iniciar sesiones.",
            responses = @ApiResponse(responseCode = "200", description = "Liberada")
    )
    @PutMapping("/{codigo}/liberar")
    public ResponseEntity<Void> liberar(
            @Parameter(description = "Código de la máquina", example = "PC02")
            @PathVariable String codigo){
        servicio.liberar(codigo);
        return ResponseEntity.ok().build();
    }
}
