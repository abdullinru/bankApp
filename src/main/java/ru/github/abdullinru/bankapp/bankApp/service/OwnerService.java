package ru.github.abdullinru.bankapp.bankApp.service;

import org.springframework.stereotype.Service;
import ru.github.abdullinru.bankapp.bankApp.dto.RequestOwnerDto;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseOwnerDto;
import ru.github.abdullinru.bankapp.bankApp.exception.OwnerNotFoundException;
import ru.github.abdullinru.bankapp.bankApp.mapper.OwnerMapper;
import ru.github.abdullinru.bankapp.bankApp.model.Account;
import ru.github.abdullinru.bankapp.bankApp.model.Owner;
import ru.github.abdullinru.bankapp.bankApp.repository.AccountRepository;
import ru.github.abdullinru.bankapp.bankApp.repository.OwnerRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final AccountRepository accountRepository;
    private final OwnerMapper mapper;

    public OwnerService(OwnerRepository ownerRepository, AccountRepository accountRepository, OwnerMapper mapper) {
        this.ownerRepository = ownerRepository;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    public ResponseOwnerDto createOwner(RequestOwnerDto ownerDto) {
        checkPin(ownerDto.pin());
        checkName(ownerDto.name());
        Owner createOwner = mapper.toOwner(ownerDto);
        Account createAccount = createAccount();
        createAccount.setOwner(createOwner);

        List<Account> accounts = new ArrayList<>();
        accounts.add(createAccount);
        createOwner.setAccounts(accounts);

        ownerRepository.save(createOwner);
        accountRepository.save(createAccount);

        return mapper.toResponseOwnerDto(createOwner);
    }

    private void checkName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("incorrect owner name");
        }
    }

    private void checkPin(String pinCode) {
        if (!pinCode.matches("\\d{4}")) {
            throw new IllegalArgumentException("incorrect pincode");
        }
    }

    public List<ResponseOwnerDto> getAllOwnres() {
        List<Owner> owners = ownerRepository.findAll();
        return mapper.toListOwnerDto(owners);
    }

    public ResponseOwnerDto createOwnerAccount(Long ownerId, String pin) {
        Owner findOwner = ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException("Owner is not found by id"));
        checkPin(pin);
        comparePin(pin, findOwner.getPin());

        Account createAccount = createAccount();
        createAccount.setOwner(findOwner);
        findOwner.getAccounts().add(createAccount);
        ownerRepository.save(findOwner);
        accountRepository.save(createAccount);
        return mapper.toResponseOwnerDto(findOwner);
    }

    private void comparePin(String pin1, String pin2) {
        if (!pin1.equals(pin2)) {
            throw new IllegalArgumentException("pin code is not match");
        }
    }

    private Account createAccount() {
        Account createAccount = new Account();
        createAccount.setBalance(BigDecimal.ZERO);
        createAccount.setNumber(createAccount.generateRandomAccountNumber());
        return createAccount;

    }
}
