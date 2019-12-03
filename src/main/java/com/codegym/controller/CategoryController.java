package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.service.BookService;
import com.codegym.service.CategoryService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BookService bookService;

    @GetMapping("/categories")
    public ModelAndView listCategory(@PageableDefault(size = 5)Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/category/list");
        modelAndView.addObject("categories", categoryService.findAll(pageable));
        return modelAndView;
    }

    @GetMapping("/category/create")
    public ModelAndView createCategoryForm() {
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/category/create")
    public ModelAndView saveCreateForm(Category category) {
        categoryService.save(category);

        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category",new Category());
        modelAndView.addObject("message", "Thêm danh mục thành công");
        return modelAndView;
    }

    @GetMapping("/category/edit/{id}")
    public ModelAndView editCategoryForm(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        if (category != null) {
            ModelAndView modelAndView = new ModelAndView("/category/edit");
            modelAndView.addObject("category", category);
            return modelAndView;
        } else {
            return new ModelAndView("error");
        }
    }

    @PostMapping("/category/edit")
    public ModelAndView updateCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);

        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category",category);
        modelAndView.addObject("message", "Cập nhật danh mục thành công");
        return modelAndView;
    }

    @GetMapping("/category/delete/{id}")
    public ModelAndView deleteCategoryForm(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);

        if (category != null) {
            ModelAndView modelAndView = new ModelAndView("/category/delete");
            modelAndView.addObject("category", category);
            return modelAndView;
        } else {
            return new ModelAndView("error");
        }
    }

    @PostMapping("/category/delete")
    public ModelAndView deleteCategory(@ModelAttribute("category") Category category, Pageable pageable) {
        categoryService.remove(category.getId());

        ModelAndView modelAndView = new ModelAndView("/category/list");
        modelAndView.addObject("categories", categoryService.findAll(pageable));
        return modelAndView;
    }

    @GetMapping("/category/view/{id}")
    public ModelAndView viewCategory(@PathVariable("id") Long id, Pageable pageable) {
        Category category = categoryService.findById(id);
        if (category != null) {
            ModelAndView modelAndView = new ModelAndView("category/view");
            modelAndView.addObject("category",category);
            modelAndView.addObject("books", bookService.findAllByCategory(category,pageable));
            return modelAndView;
        } else {
            return new ModelAndView("error");
        }
    }
}
