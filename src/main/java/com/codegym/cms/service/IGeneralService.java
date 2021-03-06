package com.codegym.cms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(Long id);

    T save(T t);

    void remove(Long id);

}

