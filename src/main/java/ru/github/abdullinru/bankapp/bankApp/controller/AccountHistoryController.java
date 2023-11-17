package ru.github.abdullinru.bankapp.bankApp.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseAccountDto;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseHistoryDto;
import ru.github.abdullinru.bankapp.bankApp.service.HistoryService;

import java.util.List;

@RestController
@RequestMapping("api/histories")
@RequiredArgsConstructor

public class AccountHistoryController {

    private final HistoryService historyService;

    @Operation(summary = "Показать историю финансовых операций по выбранному счету",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = ResponseAccountDto.class))),
            @ApiResponse(responseCode = "400", description = "incorrect arguments"),
            @ApiResponse(responseCode = "404", description = "account is not found  by id")})
    @GetMapping("/accounts/{id}")
    public List<ResponseHistoryDto> getAccountHistory(@PathVariable long id) {

        return historyService.getAccountHistory(id);

    }
}
