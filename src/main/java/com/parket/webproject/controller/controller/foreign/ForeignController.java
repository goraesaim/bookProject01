package com.parket.webproject.controller.controller.foreign;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foreign")
public class ForeignController {

    @GetMapping("/list")
    public String showDomesticList() {
        return "foreign/list";  // templates/domestic/list.html
    }
    @GetMapping("/detail")
    public String detail() {
        return "foreign/detail"; // templates/use/list.html 파일을 렌더링
    }
}
