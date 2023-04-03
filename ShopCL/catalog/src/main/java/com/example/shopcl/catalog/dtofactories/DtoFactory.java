package com.example.shopcl.catalog.dtofactories;

public interface DtoFactory<T, E> {
    T getDto(E entity);
}
