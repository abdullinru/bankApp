package ru.github.abdullinru.bankapp.bankApp.mapper;

import org.mapstruct.Mapper;
import ru.github.abdullinru.bankapp.bankApp.dto.AccountDto;
import ru.github.abdullinru.bankapp.bankApp.dto.RequestOwnerDto;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseOwnerDto;
import ru.github.abdullinru.bankapp.bankApp.model.Account;
import ru.github.abdullinru.bankapp.bankApp.model.Owner;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    Owner toOwner(RequestOwnerDto ownerDto);

    ResponseOwnerDto toResponseOwnerDto(Owner owner);

    List<AccountDto> toListAccountDto(List<Account> accounts);

    List<ResponseOwnerDto> toListOwnerDto(List<Owner> owners);

}
