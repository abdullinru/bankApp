package ru.github.abdullinru.bankapp.bankApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.github.abdullinru.bankapp.bankApp.dto.DepositDto;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseAccountDto;
import ru.github.abdullinru.bankapp.bankApp.dto.TransferDto;
import ru.github.abdullinru.bankapp.bankApp.dto.WithdrawDto;
import ru.github.abdullinru.bankapp.bankApp.service.BalanceService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
public class BankOperationController {
    private final BalanceService balanceService;

    @Operation(summary = "Внести депозит на баланс аккаунта",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = ResponseAccountDto.class))),
            @ApiResponse(responseCode = "400", description = "incorrect arguments"),
            @ApiResponse(responseCode = "404", description = "account is not found  by id")})
    @PatchMapping("/deposit")
    public ResponseAccountDto deposit(@RequestBody DepositDto depositDto) {
        Long receiverId = depositDto.accountId();
        BigDecimal depositAmount = depositDto.amount();
        return balanceService.deposit(receiverId, depositAmount);
    }

    @Operation(summary = "Снять деньги с баланса аккаунта",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = ResponseAccountDto.class))),
            @ApiResponse(responseCode = "404", description = "not found account by id"),
            @ApiResponse(responseCode = "400", description = "incorrect pin  or not enough money")})
    @PatchMapping("/withdraw")
    public ResponseAccountDto withdraw(@RequestBody WithdrawDto withdrawDto) {
        Long senderId = withdrawDto.accountId();
        BigDecimal withdrawAmount = withdrawDto.amount();
        String pin = withdrawDto.pin();
        return balanceService.withdraw(senderId, withdrawAmount, pin);
    }

    @Operation(summary = "Перевести деньги с баланса одного аккаунта на баланс другого аккаунта",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = ResponseAccountDto.class))),
            @ApiResponse(responseCode = "404", description = "not found account of sender or recipient"),
            @ApiResponse(responseCode = "400", description = "incorrect pin  or not enough money")})
    @PatchMapping("/transfer")
    public ResponseAccountDto transfer(@RequestBody TransferDto transferDto) {
        return balanceService.transfer(transferDto);
    }
}
