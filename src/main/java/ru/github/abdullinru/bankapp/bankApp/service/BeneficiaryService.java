package ru.github.abdullinru.bankapp.bankApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.github.abdullinru.bankapp.bankApp.dto.RequestBeneficiaryDto;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseBeneficiaryDto;
import ru.github.abdullinru.bankapp.bankApp.exception.BeneficiaryNotFoundException;
import ru.github.abdullinru.bankapp.bankApp.mapper.BeneficiaryMapper;
import ru.github.abdullinru.bankapp.bankApp.model.Account;
import ru.github.abdullinru.bankapp.bankApp.model.Beneficiary;
import ru.github.abdullinru.bankapp.bankApp.repository.BeneficiaryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeneficiaryService {

    private final AccountService accountService;
    private final BeneficiaryRepository beneficiaryRepository;
    private final BeneficiaryMapper mapper;

    public ResponseBeneficiaryDto createBeneficiary(RequestBeneficiaryDto beneficiaryDto) {
        checkPin(beneficiaryDto.pin());
        checkName(beneficiaryDto.name());
        Beneficiary createBeneficiary = mapper.toBeneficiary(beneficiaryDto);
        Account createAccount = accountService.createAccount();
        createAccount.setBeneficiary(createBeneficiary);

        List<Account> accounts = new ArrayList<>();
        accounts.add(createAccount);
        createBeneficiary.setAccounts(accounts);

        beneficiaryRepository.save(createBeneficiary);
        accountService.save(createAccount);

        return mapper.toResponseBeneficiaryDto(createBeneficiary);
    }

    private void checkName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("incorrect owner name");
        }
    }
    private void checkPin(String pinCode) {
        if (pinCode == null || !pinCode.matches("\\d{4}")) {
            throw new IllegalArgumentException("incorrect pincode");
        }
    }

    public List<ResponseBeneficiaryDto> getAllBeneficiaries() {
        List<Beneficiary> beneficiaries = beneficiaryRepository.findAll();
        return mapper.toListBeneficiaryDto(beneficiaries);
    }

    public ResponseBeneficiaryDto createBeneficiaryAccount(Long beneficiaryId, String pin) {
        Beneficiary findBeneficiary = beneficiaryRepository.findById(beneficiaryId).orElseThrow(() -> new BeneficiaryNotFoundException("Beneficiary is not found by id"));
        checkPin(pin);
        comparePin(pin, findBeneficiary.getPin());

        Account createAccount = accountService.createAccount();
        createAccount.setBeneficiary(findBeneficiary);
        findBeneficiary.getAccounts().add(createAccount);
        beneficiaryRepository.save(findBeneficiary);
        accountService.save(createAccount);
        return mapper.toResponseBeneficiaryDto(findBeneficiary);
    }

    private void comparePin(String pin1, String pin2) {
        if (!pin1.equals(pin2)) {
            throw new IllegalArgumentException("pin code is not match");
        }
    }
}
