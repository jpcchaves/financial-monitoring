package com.financialmonitoring.transactionservice.utils;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperUtils {

    private final ModelMapper mapper;

    public MapperUtils(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public <O, D> D map(O source, Class<D> destinationClass) {
        return mapper.map(source, destinationClass);
    }

    public <O, D> List<D> mapList(List<O> sourceList, Class<D> destinationClass) {
        return sourceList.stream()
                .map(source -> map(source, destinationClass))
                .toList();
    }
}
