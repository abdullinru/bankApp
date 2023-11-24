package ru.github.abdullinru.bankapp.bankApp.mapper;

import org.mapstruct.Mapper;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseHistoryDto;
import ru.github.abdullinru.bankapp.bankApp.model.History;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    ResponseHistoryDto toResponseHistoryDto(History history);

    List<ResponseHistoryDto> toListResponseHistoryDto(List<History> histories);
}
