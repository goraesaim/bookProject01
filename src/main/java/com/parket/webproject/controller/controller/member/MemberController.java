package com.parket.webproject.controller.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/join")
    public String showDomesticList() {
        return "member/join";
    }
    @GetMapping("/login")
    public String detail() {
        return "member/login";
    }
    @GetMapping("/complete")
    public String complete() {
        return "member/complete";
    }
}
