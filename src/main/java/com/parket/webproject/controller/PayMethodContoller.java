package com.parket.webproject.controller;

import com.parket.webproject.cofig.author.PrincipalDetailService;
import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;
import com.parket.webproject.repository.PayMethodRepository;
import com.parket.webproject.service.PayMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payMethod")
public class PayMethodContoller {

    private final PayMethodService payMethodService;
    private final PrincipalDetailService principalDetailService;
    private final PayMethodRepository payMethodRepository;

    // 등록 폼 보여주기
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("payMethod", new PayMethod());
        return "payRegister"; // templates/pay/register.html
    }

    // 등록 처리
    @PostMapping("/register")
    public String register(@AuthenticationPrincipal PrincipalDetails principal,
                       @ModelAttribute PayMethod form) {

        User user = principal.getUser();

        payMethodService.saveOrUpdatePayMethod(user, form);

        return "redirect:/mypage/payManagement";
    }

}
