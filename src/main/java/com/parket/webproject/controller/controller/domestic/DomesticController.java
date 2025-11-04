package com.parket.webproject.controller.controller.domestic;

import com.parket.webproject.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/domestic")
public class DomesticController {
    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/list")
    public String showDomesticList(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "domestic/list";  // templates/domestic/list.html
    }
    @GetMapping("/detail")
    public String detail() {
        return "domestic/detail"; // templates/use/list.html 파일을 렌더링
    }
}
