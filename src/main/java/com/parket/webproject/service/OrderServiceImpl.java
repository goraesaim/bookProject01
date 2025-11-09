package com.parket.webproject.service;

import com.parket.webproject.domain.Cart;
import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.Product;
import com.parket.webproject.domain.User;
import com.parket.webproject.dto.OrderResultDTO;
import com.parket.webproject.repository.PayMethodRepository;
import com.parket.webproject.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final CartService cartService;
    private final PayHistoryService payHistoryService;
    private final NotificationService notificationService;
    private final ProductRepository productRepository;
    private final PayMethodRepository payMethodRepository;


    @Override
    public OrderResultDTO completePayment(User user, List<Integer> cartIds) {
        List<Cart> selectedItems = cartService.getSelectedCartItems(cartIds);
        if (selectedItems.isEmpty()) {
            throw new IllegalStateException("선택된 상품이 없습니다.");
        }

        // 결제수단 확인
        List<PayMethod> payMethods = payMethodRepository.findByUser(user);
        if (payMethods.isEmpty()) {
            throw new IllegalStateException("결제수단이 없습니다.");
        }
        PayMethod payMethod = payMethods.get(0);

        // 주문번호 생성
        String orderNo = "ORD-" + System.currentTimeMillis();

        // 결제 이력 저장
        payHistoryService.savePayHistory(user, payMethod, selectedItems, orderNo);

        // 수량 및 총 가격 계산
        int totalQuantity = selectedItems.stream()
                .mapToInt(Cart::getQuantity)
                .sum();
        long totalPrice = selectedItems.stream()
                .mapToLong(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();

        // 상품 판매완료 + 알림 발송
        for (Cart cart : selectedItems) {
            Product product = cart.getProduct();
            product.setIsSold(true);
            notificationService.createNotification(
                    product.getUser(),
                    product.getTitle() + " 책이 판매 완료되었습니다!"
            );
        }

        // 장바구니 삭제
        cartService.deleteSelected(cartIds);

        // 결과 반환
        return new OrderResultDTO(orderNo, totalQuantity, totalPrice);
    }
}
