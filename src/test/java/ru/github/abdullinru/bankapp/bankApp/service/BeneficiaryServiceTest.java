package ru.github.abdullinru.bankapp.bankApp.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.abdullinru.bankapp.bankApp.dto.AccountDto;
import ru.github.abdullinru.bankapp.bankApp.dto.RequestBeneficiaryDto;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseBeneficiaryDto;
import ru.github.abdullinru.bankapp.bankApp.exception.BeneficiaryNotFoundException;
import ru.github.abdullinru.bankapp.bankApp.mapper.BeneficiaryMapper;
import ru.github.abdullinru.bankapp.bankApp.model.Account;
import ru.github.abdullinru.bankapp.bankApp.model.Beneficiary;
import ru.github.abdullinru.bankapp.bankApp.repository.AccountRepository;
import ru.github.abdullinru.bankapp.bankApp.repository.BeneficiaryRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BeneficiaryServiceTest {

    @Mock
    private BeneficiaryRepository beneficiaryRepository;
    @Mock
    private AccountRepository accountRepository;
    @Spy
    private BeneficiaryMapper mapper;
    @InjectMocks
    private BeneficiaryService beneficiaryService;

    @Test
    public void getAllBeneficiaryPositiveTest() {
        Beneficiary ruslan = new Beneficiary("ruslan", "1111");
        Beneficiary sergey = new Beneficiary("Sergey", "2222");
        Beneficiary olia = new Beneficiary("Olia", "3333");

        AccountDto ruslanAccount = new AccountDto("1111 2222 3333 4444", BigDecimal.valueOf(1000));
        AccountDto sergeyAccount = new AccountDto("2222 3333 4444 5555", BigDecimal.valueOf(2000));
        AccountDto oliaAccount = new AccountDto("3333 4444 5555 6666", BigDecimal.valueOf(3000));

        ResponseBeneficiaryDto ruslanDto = new ResponseBeneficiaryDto(1L, "ruslan", "1111", List.of(ruslanAccount));
        ResponseBeneficiaryDto sergeyDto = new ResponseBeneficiaryDto(2L, "Sergey", "2222", List.of(sergeyAccount));
        ResponseBeneficiaryDto oliaDto = new ResponseBeneficiaryDto(3L, "Olia", "3333", List.of(oliaAccount));

        List<ResponseBeneficiaryDto> beneficiaryDtos = new ArrayList<>(List.of(ruslanDto, sergeyDto, oliaDto));
        List<Beneficiary> beneficiaries = new ArrayList<>(List.of(ruslan, sergey, olia));
        Mockito.when(beneficiaryRepository.findAll()).thenReturn(beneficiaries);
        Mockito.when(mapper.toListBeneficiaryDto(beneficiaries)).thenReturn(beneficiaryDtos);

        Assertions.assertThat(beneficiaryService.getAllBeneficiaries()).isEqualTo(beneficiaryDtos);
    }

    @Test
    public void createBeneficiaryPositiveTest() {
        RequestBeneficiaryDto requestRuslanDto = new RequestBeneficiaryDto("ruslan", "1111");
        Beneficiary ruslan = new Beneficiary("ruslan", "1111");
        Account ruslanAccount = new Account("1111 2222 3333 4444", BigDecimal.valueOf(1000), ruslan);
        AccountDto ruslanAccountDto = new AccountDto("1111 2222 3333 4444", BigDecimal.valueOf(1000));

        Mockito.when(mapper.toBeneficiary(requestRuslanDto)).thenReturn(ruslan);

        ResponseBeneficiaryDto responseRuslanDto = new ResponseBeneficiaryDto(1L, "ruslan", "1111", List.of(ruslanAccountDto));

        Mockito.when(beneficiaryRepository.save(ruslan)).thenReturn(ruslan);
        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(ruslanAccount);
        Mockito.when(mapper.toResponseBeneficiaryDto(ruslan)).thenReturn(responseRuslanDto);

        Assertions.assertThat(beneficiaryService.createBeneficiary(requestRuslanDto)).isEqualTo(responseRuslanDto);
    }

    @Test
    public void createBeneficiaryNegativeTestWhenPinIncorrect() {
        RequestBeneficiaryDto requestRuslanDto = new RequestBeneficiaryDto("ruslan", "incorrect");

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> beneficiaryService.createBeneficiary(requestRuslanDto));

    }
    @Test
    public void createBeneficiaryNegativeTestWhenNameIncorrect() {
        RequestBeneficiaryDto requestRuslanDto = new RequestBeneficiaryDto("", "incorrect");

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> beneficiaryService.createBeneficiary(requestRuslanDto));

    }

    @Test
    public void createBeneficiaryAccountPositiveTest() {
        Long beneficiaryId = 1L;
        String requestPin = "1111";
        String ruslanPin = "1111";
        Beneficiary ruslan = new Beneficiary("ruslan", ruslanPin);

        Mockito.when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.of(ruslan));

        Account ruslanAccount = new Account("1111 2222 3333 4444", BigDecimal.valueOf(1000), ruslan);
        AccountDto ruslanAccountDto = new AccountDto("1111 2222 3333 4444", BigDecimal.valueOf(1000));
        List<Account> accounts = new ArrayList<>();
        ruslan.setAccounts(accounts);

        ResponseBeneficiaryDto responseRuslanDto = new ResponseBeneficiaryDto(1L, "ruslan", "1111", List.of(ruslanAccountDto));

        Mockito.when(beneficiaryRepository.save(ruslan)).thenReturn(ruslan);
        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(ruslanAccount);
        Mockito.when(mapper.toResponseBeneficiaryDto(ruslan)).thenReturn(responseRuslanDto);

        Assertions.assertThat(beneficiaryService.createBeneficiaryAccount(beneficiaryId, requestPin)).isEqualTo(responseRuslanDto);
    }
    @Test
    public void createBeneficiaryAccountNegativeTestWhenBeneficiaryNotFound() {
        Long beneficiaryId = 1L;
        String requestPin = "1111";
        String ruslanPin = "1111";
        Beneficiary ruslan = new Beneficiary("ruslan", ruslanPin);

        Mockito.when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BeneficiaryNotFoundException.class)
                .isThrownBy(() -> beneficiaryService.createBeneficiaryAccount(beneficiaryId, requestPin));

    }
    @Test
    public void createBeneficiaryAccountNegativeTestWhenPinIncorrect() {
        Long beneficiaryId = 1L;
        String requestPin = "incorrect";
        String ruslanPin = "1111";
        Beneficiary ruslan = new Beneficiary("ruslan", ruslanPin);

        Mockito.when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.of(ruslan));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> beneficiaryService.createBeneficiaryAccount(beneficiaryId, requestPin));

    }
    @Test
    public void createBeneficiaryAccountNegativeTestWhenPinNotMatch() {
        Long beneficiaryId = 1L;
        String requestPin = "2222";
        String ruslanPin = "1111";
        Beneficiary ruslan = new Beneficiary("ruslan", ruslanPin);

        Mockito.when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.of(ruslan));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> beneficiaryService.createBeneficiaryAccount(beneficiaryId, requestPin));

    }


}