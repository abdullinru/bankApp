package ru.github.abdullinru.bankapp.bankApp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.github.abdullinru.bankapp.bankApp.model.Beneficiary;

import java.util.List;
import java.util.Optional;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "beneficiary_entity-graph")
    Optional<Beneficiary> findById(Long id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "beneficiary_entity-graph")
    List<Beneficiary> findAll();
}
