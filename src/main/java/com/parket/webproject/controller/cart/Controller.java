package com.parket.webproject.controller.cart;

import com.parket.webproject.domain.Cart;
import com.parket.webproject.dto.CartDTO;
import com.parket.webproject.service.Cart.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@org.springframework.stereotype.Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Log4j2

public class Controller {
    private final CartServiceImpl cartService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addToCart(@RequestBody CartDTO cartDTO) {
        cartService.addToCart(cartDTO);
        return ResponseEntity.ok().build();
    }

    //@GetMapping("/list")
    public String goCartList() {
        return "cart/list";
    }

    @GetMapping("/list")
    public String cartList(Model model) {
        int testUserId = 1;
        //List<Cart> cartItems = cartService.findByUserId(userId);
        List<Cart> cartItems = cartService.findByUserId(testUserId);
        // 데이터 확인
        model.addAttribute("cartItems", cartItems);
        log.info(cartItems);
        return "cart/list"; // templates/cart/list.html
    }

    //선택 삭제
    @PostMapping("/deleteSelected")
    @ResponseBody
    public ResponseEntity<?> deleteSelected(@RequestBody List<Integer> cartIds) {
        cartService.deleteSelected(cartIds);
        return ResponseEntity.ok().build();
    }
}
