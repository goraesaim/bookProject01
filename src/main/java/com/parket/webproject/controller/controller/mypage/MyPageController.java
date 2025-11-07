package com.parket.webproject.controller.controller.mypage;

import com.parket.webproject.domain.PayMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    //마이페이지 - 결제수단 등록
    @GetMapping("/payManagement")
    public String payManagement(Model model) {
        model.addAttribute("payMethod", new PayMethod());
        return "mypage/payManagement";
    }
}
