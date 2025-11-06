package com.parket.webproject.controller;

import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;
import com.parket.webproject.service.PayMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payMethod")
public class PayMethodContoller {

    private final PayMethodService payMethodService;

    // 등록 폼 보여주기
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("payMethod", new PayMethod());
        return "/mypage/payManagement"; // templates/pay/register.html
    }

    // 등록 처리
    @PostMapping("/register")
    public String registerPayMethod(@ModelAttribute PayMethod payMethod) {
        // 로그인 유저 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();

        // 유저 연결 후 저장
        payMethod.setUser(user);
        payMethodService.registerPayMethod(payMethod);

        // 등록 후 다시 장바구니로 ..
        return "redirect:/cart/list";
    }
}
