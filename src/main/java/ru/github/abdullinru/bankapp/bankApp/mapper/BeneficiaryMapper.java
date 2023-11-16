package ru.github.abdullinru.bankapp.bankApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.github.abdullinru.bankapp.bankApp.dto.*;
import ru.github.abdullinru.bankapp.bankApp.model.Account;
import ru.github.abdullinru.bankapp.bankApp.model.Beneficiary;
import ru.github.abdullinru.bankapp.bankApp.model.History;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BeneficiaryMapper {

    Beneficiary toBeneficiary(RequestBeneficiaryDto beneficiaryDto);

    ResponseBeneficiaryDto toResponseBeneficiaryDto(Beneficiary beneficiary);

    List<AccountDto> toListAccountDto(List<Account> accounts);

    List<ResponseBeneficiaryDto> toListBeneficiaryDto(List<Beneficiary> beneficiaries);


    @Mapping(source = "beneficiary.name", target = "beneficiaryName")
    ResponseAccountDto toResponseAccountDto(Account account);

    ResponseHistoryDto toResponseHistoryDto(History history);

    List<ResponseHistoryDto> toListResponseHistoryDto(List<History> histories);
}
