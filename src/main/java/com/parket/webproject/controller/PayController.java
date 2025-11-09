package com.parket.webproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parket.webproject.cofig.author.PrincipalDetailService;
import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.*;
import com.parket.webproject.dto.OrderResultDTO;
import com.parket.webproject.repository.PayHistoryRepository;
import com.parket.webproject.repository.PayMethodRepository;
import com.parket.webproject.repository.UserRepository;
import com.parket.webproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class PayController {

    private final CartService cartService;
    private final PayService payMethodService;
    private final PayHistoryService payHistoryService;
    private final PrincipalDetailService principalDetailService;
    private final PayMethodRepository payMethodRepository;
    private final PayHistoryRepository payHistoryRepository;
    private final NotificationService notificationService;
    private final OrderService orderService;

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

        // 수단결제가 있는지 없는지 체크
        boolean hasPayMethod = payMethodService.hasPayMethod(user);

        //결제 수단 조회
        Optional<PayMethod> payMethodOpt = payMethodRepository.findMethodByUser(user);
        if (payMethodOpt.isPresent()) {
            model.addAttribute("payMethod", payMethodOpt.get());
        } else {
            model.addAttribute("payMethod", null);
        }

        int totalQuantity = selectedItems.stream().mapToInt(Cart::getQuantity).sum();
        long totalPrice = selectedItems.stream()
                .mapToLong(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();

        model.addAttribute("orderItems", selectedItems);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("member", user);
        model.addAttribute("cartIdsJson", cartIdsJson);
        model.addAttribute("hasPayMethod", hasPayMethod);
        return "order/pay";
    }

    //결제수단 등록했는지 안했는지 확인하기
    @PostMapping("/pay_check")
    @ResponseBody
    public ResponseEntity<String> checkPayMethod() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

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
            @AuthenticationPrincipal PrincipalDetails principal,
            @RequestParam("cartIds") String cartIdsJson,
            RedirectAttributes redirectAttributes
    ) throws JsonProcessingException {

        if (principal == null || principal.getUser() == null) {
            return "redirect:/login";
        }

        ObjectMapper mapper = new ObjectMapper();
        List<Integer> cartIds = mapper.readValue(
                cartIdsJson.replaceAll("\"", ""), new TypeReference<List<Integer>>() {}
        );

        User user = principal.getUser();

        // 전체 결제 로직 수행
        OrderResultDTO result = orderService.completePayment(user, cartIds);

        redirectAttributes.addAttribute("orderNo", result.getOrderNo());
        redirectAttributes.addFlashAttribute("totalQuantity", result.getTotalQuantity());
        redirectAttributes.addFlashAttribute("totalPrice", result.getTotalPrice());

        return "redirect:/order/complete";
    }

    @GetMapping("/complete")
    public String completePayment(@RequestParam("orderNo") String orderNo, Model model) {
        List<PayHistory> histories = payHistoryRepository.findByOrderNo(orderNo);

        model.addAttribute("orderNo", orderNo);
        model.addAttribute("histories", histories);

        return "order/complete";
    }

}
