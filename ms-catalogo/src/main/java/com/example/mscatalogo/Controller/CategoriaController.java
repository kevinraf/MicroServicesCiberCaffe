package com.example.mscatalogo.Controller;

import com.example.mscatalogo.Entity.Categoria;
import com.example.mscatalogo.Service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> listar() {
        return categoriaService.listar();
    }

    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Integer id) {
        return categoriaService.buscarPorId(id).get();
    }

    @PostMapping
    public Categoria guardar(Categoria categoria) {
        return categoriaService.guardar(categoria);
    }

    @PutMapping
    public Categoria actualizar(Categoria categoria) {
        return categoriaService.actualizar(categoria);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        categoriaService.borrarPorId(id);
        return "Categoria eliminada";
    }
}
