package com.codegym.service;

import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Iterable<Category> findAll();
    Page<Category> findAll(Pageable pageable);
    Category findById(Long id);
    void save (Category category);
    void remove(Long id);
}
