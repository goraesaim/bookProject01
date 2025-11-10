package com.parket.webproject.controller.controller.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @GetMapping("/info")
    public String info() {
        return "mypage/info";
    }
    @GetMapping("/saleComplete")
    public String saleComplete() {
        return "mypage/saleComplete";
    }

    @GetMapping("/writeList")
    public String writeList() {
        return "mypage/writeList";
    }

    @GetMapping("/orderList")
    public String orderList() {
        return "mypage/orderList";
    }

    @GetMapping("/payManagement")
    public String payManagement() {
        return "mypage/payManagement";
    }
}
