package pe.edu.upeu.mspago.Controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.mspago.DTOs.PagoDTO;
import pe.edu.upeu.mspago.DTOs.PagoRespDTO;
import pe.edu.upeu.mspago.Entidad.Pago;
import pe.edu.upeu.mspago.Servicio.PagoServicio;

import java.util.List;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoControlador {
    private final PagoServicio servicio;

    @PostMapping
    public ResponseEntity<PagoRespDTO> registrar(@RequestBody PagoDTO dto){
        return ResponseEntity.ok(servicio.registrar(dto));
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<PagoRespDTO> confirmar(@PathVariable Long id){
        return ResponseEntity.ok(servicio.confirmar(id));
    }

    @GetMapping
    public ResponseEntity<List<Pago>> listar(){
        return ResponseEntity.ok(servicio.listar());
    }
}