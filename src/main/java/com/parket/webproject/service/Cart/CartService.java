package com.parket.webproject.service.Cart;

import com.parket.webproject.domain.Cart;
import com.parket.webproject.dto.CartDTO;

import java.util.List;

public interface CartService {
    void addToCart(CartDTO cartDTO);
    List<Cart> findByUserId(int userId);
    void deleteSelected(List<Integer> cartIds);
}
