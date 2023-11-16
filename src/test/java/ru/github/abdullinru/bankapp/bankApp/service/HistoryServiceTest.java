package ru.github.abdullinru.bankapp.bankApp.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.abdullinru.bankapp.bankApp.dto.AccountDto;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseBeneficiaryDto;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseHistoryDto;
import ru.github.abdullinru.bankapp.bankApp.mapper.BeneficiaryMapper;
import ru.github.abdullinru.bankapp.bankApp.model.Beneficiary;
import ru.github.abdullinru.bankapp.bankApp.model.History;
import ru.github.abdullinru.bankapp.bankApp.model.OperationType;
import ru.github.abdullinru.bankapp.bankApp.repository.HistoryRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private BeneficiaryMapper mapper;

    @InjectMocks
    private HistoryService historyService;

    @Test
    public void getAllBeneficiaryPositiveTest() {
        Long id = 100L;
        History log1 = new History( LocalDateTime.now(), OperationType.deposit, null, 200L, BigDecimal.valueOf(100));
        History log2 = new History( LocalDateTime.now(), OperationType.withdraw, 100L, null, BigDecimal.valueOf(150));
        History log3 = new History( LocalDateTime.now(), OperationType.transfer, 100L, 200L, BigDecimal.valueOf(99));

        ResponseHistoryDto responseLog1 = new ResponseHistoryDto( 1L,LocalDateTime.now(), OperationType.deposit, null, 200L, BigDecimal.valueOf(100));
        ResponseHistoryDto responseLog2 = new ResponseHistoryDto(2L, LocalDateTime.now(), OperationType.withdraw, 100L, null, BigDecimal.valueOf(150));
        ResponseHistoryDto responseLog3 = new ResponseHistoryDto(3L, LocalDateTime.now(), OperationType.transfer, 100L, 200L, BigDecimal.valueOf(99));

        List<History> histories = new ArrayList<>(List.of( log2, log3));
        List<ResponseHistoryDto> responseHistoryDtos = new ArrayList<>(List.of(responseLog2, responseLog3));

        Mockito.when(historyRepository.findBySenderIdOrReceiverId(id, id)).thenReturn(histories);
        Mockito.when(mapper.toListResponseHistoryDto(histories)).thenReturn(responseHistoryDtos);

        Assertions.assertThat(historyService.getAccountHistory(id)).isEqualTo(responseHistoryDtos);
    }

}