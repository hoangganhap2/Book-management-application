package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.service.BookService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/books")
    public ModelAndView listBooks(@RequestParam("s")Optional<String> s,@PageableDefault(size = 10, sort = "price") Pageable pageable) {
        Page<Book> books;
        if (s.isPresent()) {
            books = bookService.findAllByNameContaining(s.get(), pageable);
        } else {
            books = bookService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping("/book/create")
    public ModelAndView createBookForm() {
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new Book());
        return modelAndView;
    }

    @PostMapping("/book/create")
    public ModelAndView saveCreateBook(@ModelAttribute("book") Book book) {
        bookService.save(book);

        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new Book());
        modelAndView.addObject("message", "Thêm mới sách thành công");
        return modelAndView;
    }

    @GetMapping("/book/edit/{id}")
    public ModelAndView editBookForm(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);

        if (book != null) {
            ModelAndView modelAndView = new ModelAndView("book/edit");
            modelAndView.addObject("book", book);
            return modelAndView;
        } else {
            return new ModelAndView("error");
        }
    }

    @PostMapping("/book/edit")
    public ModelAndView saveEditBook(@ModelAttribute("book") Book book) {
        bookService.save(book);

        ModelAndView modelAndView = new ModelAndView("/book/edit");
        modelAndView.addObject("book", book);
        modelAndView.addObject("message", "Cập nhật thông tin sách thành công");
        return modelAndView;
    }

    @GetMapping("/book/delete/{id}")
    public ModelAndView deleteBookForm(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);

        if (book != null) {
            ModelAndView modelAndView = new ModelAndView("/book/delete");
            modelAndView.addObject("book", book);
            return modelAndView;
        } else {
            return new ModelAndView("error");
        }
    }

    @PostMapping("/book/delete")
    public ModelAndView deleteBook(@ModelAttribute("book") Book book, Pageable pageable) {
        bookService.remove(book.getId());

        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", bookService.findAll(pageable));
        return modelAndView;
    }

    @GetMapping("/searchByCategory")
    public ModelAndView searchBookByCategory(@RequestParam("search") Long categoryId, Pageable pageable) {
        Page<Book> books;
        if (categoryId == -1) {
            books = bookService.findAll(pageable);
        } else {
            Category category = categoryService.findById(categoryId);
            books = bookService.findAllByCategory(category,pageable);
        }

        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", books);
        modelAndView.addObject("search", categoryId);
        return modelAndView;
    }

    @GetMapping("/sort")
    public ModelAndView sortByDate(Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", bookService.findAllByOrderByDateOfPurchaseAscPriceDesc(pageable));
        return modelAndView;
    }

    @GetMapping("/searchByCategory/{id}")
    public ModelAndView searchBookByCategoryCss(@PathVariable("id") Long id,Pageable pageable) {
        Page<Book> books;
        if (id == -1) {
            books = bookService.findAll(pageable);
        } else {
            Category category = categoryService.findById(id);
            books = bookService.findAllByCategory(category,pageable);
        }

        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", books);
        modelAndView.addObject("search", id);
        return modelAndView;
    }
}
