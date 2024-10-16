package br.com.glstecnologia.builder;


import br.com.glstecnologia.dto.TarefaDTO;
import br.com.glstecnologia.entity.Tarefa;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TarefaMapper {

    private final ModelMapper modelMapper;

    public TarefaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TarefaDTO toDTO(Tarefa model) {
        return modelMapper.map(model, TarefaDTO.class);
    }

    public Tarefa toEntity(TarefaDTO dto) {
        return modelMapper.map(dto, Tarefa.class);
    }

    public List<TarefaDTO> toListDTO(List<Tarefa> modelList) {
        return modelList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Tarefa> toList(List<TarefaDTO> dtosList) {
        return dtosList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
