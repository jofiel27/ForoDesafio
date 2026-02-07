package com.ForoHub.ForoDesafio.controller;

import com.ForoHub.ForoDesafio.domain.topicos.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository repository;

    public TopicoController(TopicoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<DatosDetalleTopico> registrar(@RequestBody @Valid DatosRegistroTopico datos) {
        if (repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            return ResponseEntity.badRequest().build();
        }
        Topico topico = new Topico();
        topico.setTitulo(datos.titulo());
        topico.setMensaje(datos.mensaje());
        topico.setAutor(datos.autor());
        topico.setCurso(datos.curso());
        repository.save(topico);
        return ResponseEntity.ok(new DatosDetalleTopico(
                topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFechaCreacion(), topico.getStatus(),
                topico.getAutor(), topico.getCurso()
        ));
    }

    @GetMapping
    public List<DatosDetalleTopico> listar() {
        return repository.findAll().stream()
                .map(t -> new DatosDetalleTopico(
                        t.getId(), t.getTitulo(), t.getMensaje(),
                        t.getFechaCreacion(), t.getStatus(),
                        t.getAutor(), t.getCurso()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> detalle(@PathVariable Long id) {
        return repository.findById(id)
                .map(t -> ResponseEntity.ok(new DatosDetalleTopico(
                        t.getId(), t.getTitulo(), t.getMensaje(),
                        t.getFechaCreacion(), t.getStatus(),
                        t.getAutor(), t.getCurso())))
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}") @Transactional
    public ResponseEntity<DatosDetalleTopico> actualizar(@PathVariable Long id, @RequestBody DatosActualizacionTopico datos) {
        return repository.findById(id).map(t -> {
            if (datos.titulo() != null) t.setTitulo(datos.titulo());
            if (datos.mensaje() != null) t.setMensaje(datos.mensaje());
            if (datos.autor() != null) t.setAutor(datos.autor());
            if (datos.curso() != null) t.setCurso(datos.curso());
            return ResponseEntity.ok(new DatosDetalleTopico(
                    t.getId(),
                    t.getTitulo(),
                    t.getMensaje(),
                    t.getFechaCreacion(),
                    t.getStatus(),
                    t.getAutor(),
                    t.getCurso()));
        }).orElse(ResponseEntity.notFound().build()); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
