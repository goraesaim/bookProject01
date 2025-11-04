package com.parket.webproject.controller.controller.domestic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/domestic")
public class DomesticController {

    @GetMapping("/list")
    public String showDomesticList() {
        return "domestic/list";  // templates/domestic/list.html
    }
    @GetMapping("/detail")
    public String detail() {
        return "domestic/detail"; // templates/use/list.html 파일을 렌더링
    }
}
