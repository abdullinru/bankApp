package ru.github.abdullinru.bankapp.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.github.abdullinru.bankapp.bankApp.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
