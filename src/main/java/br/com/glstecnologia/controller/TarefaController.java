package br.com.glstecnologia.controller;

import br.com.glstecnologia.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.glstecnologia.dto.TarefaDTO;
import br.com.glstecnologia.service.TarefaService;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> listarTodas() {
        return ResponseEntity.ok(tarefaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TarefaDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TarefaDTO>> buscarPorStatus(@PathVariable Status status) {
        return ResponseEntity.ok(tarefaService.filtrarPorStatus(status));
    }

    @GetMapping("/server-live")
    public ResponseEntity<String> isAlive() {
        return ResponseEntity.ok("Is live");
    }

    @PostMapping
    public ResponseEntity<TarefaDTO> salvar(@RequestBody TarefaDTO tarefaDTO) {
        return ResponseEntity.ok(tarefaService.salvar(tarefaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<TarefaDTO>> atualizar(@RequestBody TarefaDTO tarefaDTO) {
        return ResponseEntity.ok(tarefaService.atualizar(tarefaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
