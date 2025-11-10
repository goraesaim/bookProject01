package com.parket.webproject.controller.controller.mypage;

import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;
import com.parket.webproject.dto.ProductDTO;
import com.parket.webproject.repository.member.MemberRepository;
<<<<<<< HEAD
import jakarta.servlet.http.HttpSession;
=======
import com.parket.webproject.service.ProductService;
>>>>>>> 400af16a2bdaecdf19a6652e0398320a1aab5f3c
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/mypage")
@Log4j2
public class MyPageController {
    //코드추가
    private final PasswordEncoder passwordEncoder; // 로그인 유지용
    private final HttpSession session;
    // 여기까지 코드 추가

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final ProductService productService;

<<<<<<< HEAD
    public MyPageController(PasswordEncoder passwordEncoder, HttpSession session, BCryptPasswordEncoder bCryptPasswordEncoder, MemberRepository memberRepository) {
        this.passwordEncoder = passwordEncoder;
        this.session = session;
=======
    public MyPageController(BCryptPasswordEncoder bCryptPasswordEncoder, MemberRepository memberRepository, ProductService productService) {
>>>>>>> 400af16a2bdaecdf19a6652e0398320a1aab5f3c
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberRepository = memberRepository;
        this.productService = productService;
    }

    @GetMapping("/info")
    public void info(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        log.info("마이페이지 진입");
        User user = principal.getUser(); // 로그인한 사용자 정보 가져오기
        model.addAttribute("user", user);
    }

    @GetMapping("/infoModify")
    public void infoModifyGet(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        log.info("내 정보 수정 페이지 입장");
        User user = principal.getUser(); // 로그인한 사용자 정보 가져오기
        model.addAttribute("user", user);
    }

    @PostMapping("/infoModify")
    public String infoModifyPost(@AuthenticationPrincipal PrincipalDetails principal, @ModelAttribute User newInfo) {
        User currentUser = principal.getUser(); // 로그인한 사용자 정보 가져오기
        // 비밀번호 변경 처리 (비어있지 않을 경우만)
        if (newInfo.getPassword() != null && !newInfo.getPassword().trim().isEmpty()) {
            String encodedPassword = bCryptPasswordEncoder.encode(newInfo.getPassword());
            currentUser.setPassword(encodedPassword);
        }
        // 주소 변경 처리 (비어있지 않을 경우만)
        if (newInfo.getAddress() != null && !newInfo.getAddress().trim().isEmpty()) {
            currentUser.setAddress(newInfo.getAddress());
        }

        memberRepository.save(currentUser);
        return "redirect:/mypage/info";
    }


    @GetMapping("/saleComplete")
    public void saleComplete(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        log.info("판매완료 도서 진입");
        User user = principal.getUser(); // 로그인한 사용자 정보 가져오기
        model.addAttribute("user", user);
    }

    @GetMapping("/writeList")
    public void writeList(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        log.info("작성한 글 진입");
        User user = principal.getUser(); // 로그인한 사용자 정보 가져오기
        model.addAttribute("user", user);

        List<ProductDTO> products = productService.findProductsByUserId(user.getId());
        model.addAttribute("products", products);
        model.addAttribute("productCount", products.size());
    }

    @GetMapping("/orderList")
    public void orderList(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        log.info("나의 주문내역 진입");
        User user = principal.getUser(); // 로그인한 사용자 정보 가져오기
        model.addAttribute("user", user);
    }

    //마이페이지 - 결제수단 등록
    @GetMapping("/payManagement")
    public String payManagement(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        log.info("결제수단 관리 진입");
        User user = principal.getUser(); // 로그인한 사용자 정보 가져오기
        model.addAttribute("user", user);

        model.addAttribute("payMethod", new PayMethod());
        return "mypage/payManagement";
    }
    //여기서 부터 코드추가
    @GetMapping("/withdrawal")
    public String withdrawalPage() {
        return "mypage/withdrawal"; // 탈퇴 확인 페이지로 이동
    }

    @PostMapping("/withdrawal")
    public String withdrawal(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();

        // 사용자 삭제
        memberRepository.deleteById(user.getId());

        // 세션 초기화
        session.invalidate();

        // 탈퇴 후 메인 페이지로 리다이렉트
        return "redirect:/?withdrawalSuccess";
    }

}
