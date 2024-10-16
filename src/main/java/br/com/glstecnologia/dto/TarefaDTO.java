package br.com.glstecnologia.dto;

import java.util.Date;

import br.com.glstecnologia.entity.Status;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Status status;
    private Date dataCriacao;

}
