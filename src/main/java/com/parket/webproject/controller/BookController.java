package com.parket.webproject.controller;


import com.parket.webproject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;

    @GetMapping("/view")
    public String showBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "BookHtml";
    }

}
