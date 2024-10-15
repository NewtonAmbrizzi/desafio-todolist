package br.com.glstecnologia.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.glstecnologia.entity.Status;
import br.com.glstecnologia.entity.Tarefa;
import br.com.glstecnologia.repository.TarefaRepository;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    // Listar tarefas com ordenação dinâmica e filtro por status
    public List<Tarefa> listarTarefasOrdenadas(String campoOrdenacao, String direcaoOrdenacao, Status status) {
        // Se não for fornecido um campo de ordenação, usar "dataCriacao" como padrão
        if (campoOrdenacao == null || campoOrdenacao.isEmpty()) {
            campoOrdenacao = "dataCriacao";  // Campo padrão
        }

        // Se não for fornecida uma direção de ordenação, usar "desc" como padrão
        if (direcaoOrdenacao == null || direcaoOrdenacao.isEmpty()) {
            direcaoOrdenacao = "desc";  // Direção padrão
        }

        // Definindo a direção da ordenação
        Sort.Direction direction;
        if ("desc".equalsIgnoreCase(direcaoOrdenacao)) {
            direction = Sort.Direction.DESC;
        } else if ("asc".equalsIgnoreCase(direcaoOrdenacao)) {
            direction = Sort.Direction.ASC;
        } else {
            throw new IllegalArgumentException("Direção de ordenação inválida: " + direcaoOrdenacao);
        }

        // Retornar a lista de tarefas ordenada e filtrada
        if (status != null) {
            return tarefaRepository.findByStatus(status, Sort.by(direction, campoOrdenacao));
        } else {
            return tarefaRepository.findAll(Sort.by(direction, campoOrdenacao));
        }
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
