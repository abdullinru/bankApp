package ru.github.abdullinru.bankapp.bankApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ru.github.abdullinru.bankapp.bankApp.dto.RequestOwnerDto;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseOwnerDto;
import ru.github.abdullinru.bankapp.bankApp.service.AccountService;
import ru.github.abdullinru.bankapp.bankApp.service.OwnerService;

import java.util.List;

@RestController
@RequestMapping("/api/bank")
public class OwnerAccountController {

    private final OwnerService ownerService;

    public OwnerAccountController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Operation(summary = "Получить всех владельцев счетов",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseOwnerDto.class)))
    })
    @GetMapping
    public List<ResponseOwnerDto> getAllOwners() {
        return ownerService.getAllOwnres();
    }

    @Operation(summary = "Создать владельца счета",
            description = "необходимо указать имя и пин-код. Номер счета создается автоматически")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseOwnerDto.class))),
            @ApiResponse(responseCode = "400", description = "illegal arguments: pin-code or name")})
    @PostMapping
    public ResponseEntity<ResponseOwnerDto> createOwner(@RequestBody RequestOwnerDto ownerDto) {
        ResponseOwnerDto result = ownerService.createOwner(ownerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Создать новый счет для владельца",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseOwnerDto.class))),
            @ApiResponse(responseCode = "404", description = "owner is not found by id"),
            @ApiResponse(responseCode = "400", description = "illegal arguments: pin code is not correct or not match")})
    @PostMapping("/{id}")
    public ResponseEntity<ResponseOwnerDto> createAccount(
            @RequestBody String pin, @PathVariable Long id) {
        ResponseOwnerDto result = ownerService.createOwnerAccount(id, pin);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
