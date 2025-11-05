package com.parket.webproject.service.Cart;

import com.parket.webproject.domain.Cart;
import com.parket.webproject.domain.Product;
import com.parket.webproject.dto.CartDTO;
import com.parket.webproject.repository.cart.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

@Log4j2
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    // 장바구니 추가
    @Override
    public void addToCart(CartDTO cartDTO) {
        Cart cart = Cart.builder()
                .userId(cartDTO.getUserId())
                .product(Product.builder().productId(cartDTO.getProductId()).build())  // data-num으로 넘어온 값
                .quantity(cartDTO.getQuantity())
                .addedAt(LocalDateTime.now())
                .build();
        cartRepository.save(cart);
    }

    // 장바구니 리스트 뿌리기
    public List<Cart> findByUserId(int userId){
        return cartRepository.findByUserId(userId);
    }

    @Transactional
    @Override
    public void deleteSelected(List<Integer> cartIds) {
        cartRepository.deleteAllById(cartIds);
    }

}
