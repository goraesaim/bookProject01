package com.parket.webproject.controller;


import com.parket.webproject.domain.CrawlBook;
import com.parket.webproject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;

    @GetMapping("/view")
    public String showBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "BookHtml";
    }
    @GetMapping("/books/category")
    public String getBooksByCategory(@RequestParam String keyword, Model model) {
        List<CrawlBook> books = bookRepository.findByCategoryContaining("%" + keyword + "%");
        model.addAttribute("books", books);
        return "BookList :: bookList";
    }


}
