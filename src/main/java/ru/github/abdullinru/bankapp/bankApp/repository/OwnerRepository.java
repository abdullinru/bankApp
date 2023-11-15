package ru.github.abdullinru.bankapp.bankApp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.github.abdullinru.bankapp.bankApp.model.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "owner_entity-graph")
    Optional<Owner> findById(Long id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "owner_entity-graph")
    List<Owner> findAll();
}
