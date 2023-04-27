package com.example.shopcl.dto;

public interface DtoFactory <D, E> {
    D getDto(E entity);
}
