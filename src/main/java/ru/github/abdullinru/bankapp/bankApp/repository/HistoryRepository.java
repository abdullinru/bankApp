package ru.github.abdullinru.bankapp.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.github.abdullinru.bankapp.bankApp.model.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
