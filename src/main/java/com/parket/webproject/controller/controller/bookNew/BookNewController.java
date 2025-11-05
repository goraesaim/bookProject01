package com.parket.webproject.controller.controller.bookNew;

import com.parket.webproject.repository.BookRepository;
import com.parket.webproject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookNew")
public class BookNewController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public String showDomesticList(Model model) {
        model.addAttribute("books", bookRepository.findByBookNewAll());
        return "bookNew/list";  // templates/bookNew/list.html
    }
    @GetMapping("/search")
    public String searchDomesticList(String keyword,Model model) {
        model.addAttribute("books", bookRepository.findBySearchBookNewAll("%" + keyword + "%"));
        model.addAttribute("keyword", keyword);
        return "bookNew/list";  // templates/bookNew/list.html
    }
    @GetMapping("/detail")
    public String detail(Long bno, Model model) {
        model.addAttribute("book", bookService.findBookById(bno));
        return "bookNew/detail"; // templates/use/list.html 파일을 렌더링
    }
}
