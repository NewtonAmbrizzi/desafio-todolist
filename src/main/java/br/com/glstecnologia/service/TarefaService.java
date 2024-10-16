package br.com.glstecnologia.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.glstecnologia.dto.TarefaDTO;
import br.com.glstecnologia.entity.Status;
import br.com.glstecnologia.entity.Tarefa;
import br.com.glstecnologia.builder.TarefaMapper;
import br.com.glstecnologia.repository.TarefaRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    private TarefaMapper tarefaMapper;

    @Autowired
    private TarefaRepository tarefaRepository;

    /**
     * Lista todas as tarefas
     */
    public List<TarefaDTO> listarTodas() {
        return tarefaRepository.findAll().stream()
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma tarefa por ID e retorna a lista completa
     */
    public List<TarefaDTO> buscarPorId(Long id) {
        tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        return listarTodas();
    }

    /**
     * Salva uma nova tarefa e retorna a lista completa de tarefas
     */
    public TarefaDTO salvar(TarefaDTO tarefaDTO) {
        tarefaDTO.setDataCriacao(new Date());
        Tarefa tarefa = tarefaMapper.toEntity(tarefaDTO);
        return tarefaMapper.toDTO(tarefaRepository.save(tarefa));
    }

    /**
     * Atualiza uma tarefa e retorna a lista completa de tarefas
     */
    public List<TarefaDTO> atualizar(TarefaDTO tarefaDTO) {
        Tarefa tarefa = tarefaRepository.findById(tarefaDTO.getId())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        
        tarefa.setTitulo(tarefaDTO.getTitulo());
        tarefa.setDescricao(tarefaDTO.getDescricao());
        tarefa.setStatus(tarefaDTO.getStatus());
        
        tarefaRepository.save(tarefa);
        return listarTodas();
    }

    /**
     * Deleta uma tarefa e retorna a lista completa de tarefas
     */
    public List<TarefaDTO> deletar(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        
        tarefaRepository.delete(tarefa);
        return listarTodas();
    }

    /**
     * Ordena a lista de tarefas por um campo específico e uma direção (ascendente ou descendente)
     */
    public List<TarefaDTO> ordenar(String campoOrdenacao, String direcaoOrdenacao) {
        Sort sort = direcaoOrdenacao.equalsIgnoreCase("desc")
                ? Sort.by(Sort.Order.desc(campoOrdenacao))
                : Sort.by(Sort.Order.asc(campoOrdenacao));

        return tarefaRepository.findAll(sort).stream()
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Filtra tarefas pelo status e retorna a lista completa de tarefas filtradas
     */
    public List<TarefaDTO> filtrarPorStatus(Status status) {
        return tarefaRepository.findByStatus(status).stream()
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
