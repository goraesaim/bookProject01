package com.parket.webproject.controller.controller.member;


import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.User;
import com.parket.webproject.repository.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    public final MemberRepository memberRepository;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/join")
    public void join(){

    }

//        System.out.println("---------------------------------------------------------");
//        System.out.println(user);
//        System.out.println("---------------------------------------------------------");
    @Transactional
    @PostMapping("/register")
    public String register(User user){
        String encPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encPassword);
        user.setRole("USER");
        try{
            memberRepository.save(user);
            System.out.println("회원 저장 완료" + user.getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/member/complete";
    }

    @GetMapping("/check")
    public String myPageInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("현재 로그인 사용자: " + auth.getName());
        System.out.println("권한: " + auth.getAuthorities());
        return "redirect:/";
    }

    @GetMapping("/check-username")
    @ResponseBody
    public Map<String, Boolean> checkUsername(@RequestParam String username) {
        boolean exists = memberRepository.existsByUsername(username);
        return Map.of("exists", exists);
    }


    @GetMapping("/login")
    public void login(){

    }
    @GetMapping("/complete")
    public String complete() {
        return "member/complete";
    }


    @PostMapping("/withdrawal")
    public String withdrawAccount(HttpServletRequest request, @AuthenticationPrincipal PrincipalDetails principal) {
        User user = principal.getUser();

        memberRepository.delete(user);
        request.getSession().invalidate();
        return "redirect:/?withdrawalSuccess=true";
    }
    @GetMapping("/withdrawal")
    public String withdrawalPage() {
        return "mypage/withdrawal"; // join.html 파일이 mypage 폴더 안에 있으면 경로 맞춰주세요
    }
}
