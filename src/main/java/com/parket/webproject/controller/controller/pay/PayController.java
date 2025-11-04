package com.parket.webproject.controller.controller.pay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class PayController {

    @GetMapping("/pay")
    public String pay() {
        return "order/pay";

    }
    @GetMapping("/complete")
    public String complete() {
        return "order/complete";

    }
}
