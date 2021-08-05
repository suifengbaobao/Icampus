package com.icampus.common.service;

public interface Function<T, E> {
    T callback(E e);
}
