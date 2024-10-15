package br.com.glstecnologia.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.glstecnologia.entity.Status;
import br.com.glstecnologia.entity.Tarefa;
import br.com.glstecnologia.repository.TarefaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    // Listar todas as tarefas
    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    // Buscar tarefa por ID
    public Optional<Tarefa> buscarPorId(Long id) {
        return tarefaRepository.findById(id);
    }

    // Criar ou atualizar uma tarefa
    public Tarefa salvarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    // Deletar uma tarefa por ID
    public void deletarTarefa(Long id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Tarefa com ID " + id + " não encontrada.");
        }
    }

    // Atualizar status de uma tarefa
    public Tarefa atualizarStatus(Long id, Status novoStatus) {
        Optional<Tarefa> tarefaExistente = tarefaRepository.findById(id);
        
        if (tarefaExistente.isPresent()) {
            Tarefa tarefa = tarefaExistente.get();
            tarefa.setStatus(novoStatus);
            return tarefaRepository.save(tarefa);
        } else {
            throw new IllegalArgumentException("Tarefa com ID " + id + " não encontrada.");
        }
    }
}
