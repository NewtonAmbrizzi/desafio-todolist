package br.com.glstecnologia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import br.com.glstecnologia.entity.Status;
import br.com.glstecnologia.entity.Tarefa;

import java.util.List;

@Repository
@EnableJpaRepositories(basePackages = "br.com.glstecnologia.repository")
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByStatus(Status status);

}
