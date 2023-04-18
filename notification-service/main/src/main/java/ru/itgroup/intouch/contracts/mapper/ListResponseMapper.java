package ru.itgroup.intouch.contracts.mapper;

import ru.itgroup.intouch.dto.response.ResponseDto;

import java.util.List;

public interface ListResponseMapper<T> {
    ResponseDto getDestination(List<T> objects);
}
