package com.parket.webproject.controller.controller.use;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/use")
public class UseController {

    @GetMapping("/list")
    public String list() {
        return "use/list"; // templates/use/list.html 파일을 렌더링
    }

    @GetMapping("/write")
    public String write() {
        return "use/write"; // templates/use/list.html 파일을 렌더링
    }

}
