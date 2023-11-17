package ru.github.abdullinru.bankapp.bankApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseHistoryDto;
import ru.github.abdullinru.bankapp.bankApp.mapper.BeneficiaryMapper;
import ru.github.abdullinru.bankapp.bankApp.model.History;
import ru.github.abdullinru.bankapp.bankApp.repository.HistoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final BeneficiaryMapper mapper;

    public List<ResponseHistoryDto> getAccountHistory(long id) {
        List<History> accountHistories = historyRepository.findBySenderIdOrReceiverId(id, id);
        return mapper.toListResponseHistoryDto(accountHistories);

    }
}
