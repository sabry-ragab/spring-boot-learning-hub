package com.kfh.education.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMapper<D, E> {

    @Autowired
    private ModelMapper modelMapper;

    public D toDto(E entity) {
        D dto = modelMapper.map(entity, getDtoClass());
        afterMapEntityToDto(entity, dto);
        return dto;
    }

    public List<D> toDtoList(List<E> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public E toEntity(D dto) {
        E entity = modelMapper.map(dto, getEntityClass());
        afterMapDtoToEntity(entity, dto);
        return entity;
    }

    public List<E> toEntityList(List<D> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    protected void afterMapEntityToDto(E entity, D dto) {

    }

    protected void afterMapDtoToEntity(E entity, D dto) {

    }

    public abstract Class<D> getDtoClass();

    public abstract Class<E> getEntityClass();

}
