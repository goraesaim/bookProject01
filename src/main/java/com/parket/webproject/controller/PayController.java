package com.parket.webproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parket.webproject.cofig.author.PrincipalDetailService;
import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.Cart;
import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;
import com.parket.webproject.repository.PayMethodRepository;
import com.parket.webproject.repository.UserRepository;
import com.parket.webproject.service.CartService;
import com.parket.webproject.service.PayHistoryService;
import com.parket.webproject.service.PayMethodService;
import com.parket.webproject.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class PayController {

    private final CartService cartService;
    private final PayService payMethodService;
    private final PayHistoryService payHistoryService;
    private final PrincipalDetailService principalDetailService;
    private final PayMethodRepository payMethodRepository;


    @GetMapping("/pay")
    public String pay() {
        return "order/pay";
    }
    @PostMapping("/pay")
    public String orderPay(
            @RequestParam("cartIds") String cartIdsJson,
            @AuthenticationPrincipal PrincipalDetails principal,
            Model model) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        List<Integer> cartIds = mapper.readValue(cartIdsJson, new TypeReference<List<Integer>>() {});
        List<Cart> selectedItems = cartService.getSelectedCartItems(cartIds);

        //로그인 유저 없으면 ..
        if (principal == null || principal.getUser() == null) {
            return "redirect:/member/login";
        }
        User user = principal.getUser();

        int totalQuantity = selectedItems.stream().mapToInt(Cart::getQuantity).sum();
        long totalPrice = selectedItems.stream()
                .mapToLong(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();

        model.addAttribute("orderItems", selectedItems);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("member", user);
        model.addAttribute("cartIdsJson", cartIdsJson);
        return "order/pay";
    }

    //결제수단 등록했는지 안했는지 확인하기
    @PostMapping("/pay_check")
    @ResponseBody
    public ResponseEntity<String> checkPayMethod() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
//            return ResponseEntity.status(401).body("UNAUTHORIZED");
//        }

        //로그인 회원
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();

        boolean hasPayMethod = payMethodService.hasPayMethod(user);

        if (hasPayMethod) {
            return ResponseEntity.ok("OK"); // 결제 진행 가능
        } else {
            return ResponseEntity.ok("NO"); // 결제수단 없음
        }
    }

    // 결제 완료시!!!!!!!!!!
    @PostMapping("/complete")
    public String completePayment(
            @RequestParam("cartIds") String cartIdsJson,
            @AuthenticationPrincipal PrincipalDetails principal, Model model
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        List<Integer> cartIds = mapper.readValue(
                cartIdsJson.replaceAll("\"", ""), new TypeReference<List<Integer>>() {}
        );

        List<Cart> selectedItems = cartService.getSelectedCartItems(cartIds);
        User user = principal.getUser();
        
        //주문번호 생성
        String orderNo = "ORD-" + System.currentTimeMillis();
        PayMethod payMethod = payMethodRepository.findByUser(user).get(0);
        payHistoryService.savePayHistory(user, payMethod, selectedItems, orderNo);

        model.addAttribute("orderNo", orderNo);
//        model.addAttribute("totalQuantity", totalQuantity);
//        model.addAttribute("totalPrice", totalPrice);
//        model.addAttribute("orderItems", selectedItems);

        return "order/complete";
    }

    @GetMapping("/complete")
    public String completePayment(@AuthenticationPrincipal PrincipalDetails principal) {
        // 결제 완료 후 PayHistory 저장 로직
        return "order/complete";
    }

}
