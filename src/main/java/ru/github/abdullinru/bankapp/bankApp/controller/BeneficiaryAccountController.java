package ru.github.abdullinru.bankapp.bankApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ru.github.abdullinru.bankapp.bankApp.dto.RequestBeneficiaryDto;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseBeneficiaryDto;
import ru.github.abdullinru.bankapp.bankApp.service.BeneficiaryService;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiaries")
@RequiredArgsConstructor
public class BeneficiaryAccountController {

    private final BeneficiaryService beneficiaryService;

    @Operation(summary = "Получить всех владельцев счетов",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseBeneficiaryDto.class)))
    })
    @GetMapping
    public List<ResponseBeneficiaryDto> getAllBeneficiaries() {
        return beneficiaryService.getAllBeneficiaries();
    }

    @Operation(summary = "Создать владельца счета",
            description = "необходимо указать имя и пин-код. Номер счета создается автоматически")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseBeneficiaryDto.class))),
            @ApiResponse(responseCode = "400", description = "illegal arguments: pin-code or name")})
    @PostMapping
    public ResponseEntity<ResponseBeneficiaryDto> createBeneficiary(@RequestBody RequestBeneficiaryDto ownerDto) {
        ResponseBeneficiaryDto result = beneficiaryService.createBeneficiary(ownerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Создать новый счет для владельца",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseBeneficiaryDto.class))),
            @ApiResponse(responseCode = "404", description = "owner is not found by id"),
            @ApiResponse(responseCode = "400", description = "illegal arguments: pin code is not correct or not match")})
    @PostMapping("/{id}/accounts")
    public ResponseEntity<ResponseBeneficiaryDto> createBeneficiaryAccount(
            @RequestBody String pin, @PathVariable Long id) {
        ResponseBeneficiaryDto result = beneficiaryService.createBeneficiaryAccount(id, pin);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
