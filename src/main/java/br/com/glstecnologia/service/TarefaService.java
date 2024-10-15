package br.com.glstecnologia.service;


import br.com.glstecnologia.entity.Status;
import br.com.glstecnologia.entity.Tarefa;
import br.com.glstecnologia.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    // Criar uma nova tarefa
    public Tarefa criarTarefa(Tarefa novaTarefa) {
        return tarefaRepository.save(novaTarefa);
    }

    // Atualizar uma tarefa existente
    public Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada) {
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        if (tarefaOptional.isPresent()) {
            Tarefa tarefa = tarefaOptional.get();
            tarefa.setTitulo(tarefaAtualizada.getTitulo());
            tarefa.setDescricao(tarefaAtualizada.getDescricao());
            tarefa.setStatus(tarefaAtualizada.getStatus());
            // Atualizar outros campos conforme necessário
            return tarefaRepository.save(tarefa);
        } else {
            throw new IllegalArgumentException("Tarefa não encontrada com ID: " + id);
        }
    }

    // Deletar uma tarefa
    public void deletarTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }

    // Obter uma tarefa específica
    public Tarefa obterTarefa(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada com ID: " + id));
    }
}
