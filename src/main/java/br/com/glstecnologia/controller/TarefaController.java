package br.com.glstecnologia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.glstecnologia.entity.Status;
import br.com.glstecnologia.entity.Tarefa;
import br.com.glstecnologia.service.TarefaService;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    // Endpoint para listar tarefas com filtros e ordenação
    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTarefas(
            @RequestParam(required = false) String campoOrdenacao,
            @RequestParam(required = false) String direcaoOrdenacao,
            @RequestParam(required = false) Status status) {
        List<Tarefa> tarefas = tarefaService.listarTarefasOrdenadas(campoOrdenacao, direcaoOrdenacao, status);
        return ResponseEntity.ok(tarefas);
    }

    // Endpoint para criar uma nova tarefa
    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa novaTarefa) {
        Tarefa tarefaCriada = tarefaService.criarTarefa(novaTarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaCriada);
    }

    // Endpoint para atualizar uma tarefa existente
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        Tarefa tarefa = tarefaService.atualizarTarefa(id, tarefaAtualizada);
        return ResponseEntity.ok(tarefa);
    }

    // Endpoint para deletar uma tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para obter uma tarefa específica
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> obterTarefa(@PathVariable Long id) {
        Tarefa tarefa = tarefaService.obterTarefa(id);
        return ResponseEntity.ok(tarefa);
    }
}