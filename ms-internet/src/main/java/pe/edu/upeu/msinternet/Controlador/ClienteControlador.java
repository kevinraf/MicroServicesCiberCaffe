package pe.edu.upeu.msinternet.Controlador;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.msinternet.Dto.ClienteDto;
import pe.edu.upeu.msinternet.Servicio.ClienteServicio;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(
        name = "Clientes",
        description = "Gestión de clientes del local. Los clientes se identifican por DNI. " +
                "La aplicación acumula horas consumidas por cada cliente."
)
@SecurityRequirement(name = "bearerAuth")
public class ClienteControlador {
    private final ClienteServicio servicio;

    @Operation(
            summary = "Registrar cliente",
            description = "Crea un nuevo cliente con DNI, nombre y datos de contacto."
                    + "<br><b>Lógica de negocio:</b> el cliente permite asociar sesiones y acumular horas consumidas.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteDto.class),
                            examples = @ExampleObject(name = "NuevoCliente",
                                    value = "{\n" +
                                            "  \"dni\": \"11223344\",\n" +
                                            "  \"nombreCompleto\": \"Ana Torres\",\n" +
                                            "  \"telefono\": \"999888777\",\n" +
                                            "  \"correo\": \"ana.torres@example.com\"\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente creado",
                            content = @Content(schema = @Schema(implementation = ClienteDto.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping
    public ResponseEntity<ClienteDto> registrar(@RequestBody ClienteDto dto){
        return ResponseEntity.ok(servicio.registrar(dto));
    }

    @Operation(
            summary = "Buscar cliente por DNI",
            description = "Obtiene un cliente existente a partir del DNI."
                    + "<br><b>Lógica de negocio:</b> permite validar si el cliente ya existe para iniciar sesiones.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                            content = @Content(schema = @Schema(implementation = ClienteDto.class),
                                    examples = @ExampleObject(name = "ClienteExistente",
                                            value = "{\n" +
                                                    "  \"id\": 1,\n" +
                                                    "  \"dni\": \"12345678\",\n" +
                                                    "  \"nombreCompleto\": \"Juan Pérez\",\n" +
                                                    "  \"telefono\": \"987654321\",\n" +
                                                    "  \"correo\": \"juan.perez@example.com\",\n" +
                                                    "  \"horasAcumuladas\": 5\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "No existe cliente con ese DNI")
            }
    )
    @GetMapping("/{dni}")
    public ResponseEntity<ClienteDto> porDni(
            @Parameter(description = "DNI del cliente", example = "12345678")
            @PathVariable String dni){
        return ResponseEntity.ok(servicio.buscarPorDni(dni));
    }

    @Operation(
            summary = "Listar clientes",
            description = "Retorna todos los clientes registrados.",
            responses = @ApiResponse(responseCode = "200", description = "OK")
    )
    @GetMapping
    public ResponseEntity<List<ClienteDto>> listar(){
        return ResponseEntity.ok(servicio.listar());
    }
}
