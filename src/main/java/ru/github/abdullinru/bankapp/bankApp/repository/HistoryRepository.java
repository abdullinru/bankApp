package ru.github.abdullinru.bankapp.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.github.abdullinru.bankapp.bankApp.model.History;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
}
