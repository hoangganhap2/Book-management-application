package com.codegym.service;

import com.codegym.model.Book;
import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookService  {
    Page<Book> findAll(Pageable pageable);
    Book findById(Long id);
    void save(Book book);
    void remove(Long id);
    Page<Book> findAllByCategory(Category category, Pageable pageable);
    Page<Book> findAllByOrderByDateOfPurchaseAscPriceDesc(Pageable pageable);
    Page<Book> findAllByNameContaining(String s, Pageable pageable);
}
