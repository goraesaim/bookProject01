package com.parket.webproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.Cart;
import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;
import com.parket.webproject.repository.UserRepository;
import com.parket.webproject.service.CartService;
import com.parket.webproject.service.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class PayController {

    private final CartService cartService;
    private final UserRepository userRepository;

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

        User member = principal.getUser();

        int totalQuantity = selectedItems.stream().mapToInt(Cart::getQuantity).sum();
        long totalPrice = selectedItems.stream()
                .mapToLong(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();

        model.addAttribute("orderItems", selectedItems);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("member", member);

        return "order/pay";
    }


    @GetMapping("/complete")
    public String complete() {
        return "order/complete";
    }


}
