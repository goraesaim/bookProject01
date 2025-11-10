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
        if (cartIds == null || cartIds.isEmpty()) {
            throw new IllegalArgumentException("cartIds가 비어있습니다. (바로구매는 다른 메서드 사용)");
        }

        List<Cart> selectedItems = cartService.getSelectedCartItems(cartIds);

        List<PayMethod> payMethods = payMethodRepository.findByUser(user);
        if (payMethods.isEmpty()) {
            throw new IllegalStateException("결제수단이 없습니다.");
        }
        PayMethod payMethod = payMethods.get(0);

        String orderNo = "ORD-" + System.currentTimeMillis();

        payHistoryService.savePayHistory(user, payMethod, selectedItems, orderNo);

        int totalQuantity = selectedItems.stream().mapToInt(Cart::getQuantity).sum();
        long totalPrice = selectedItems.stream()
                .mapToLong(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();

        for (Cart cart : selectedItems) {
            Product product = cart.getProduct();
            product.setIsSold(true);
            notificationService.createNotification(
                    product.getUser(),
                    product.getTitle() + " 책이 판매 완료되었습니다!"
            );
        }

        cartService.deleteSelected(cartIds);
        return new OrderResultDTO(orderNo, totalQuantity, totalPrice);
    }

    @Override
    public OrderResultDTO completeDirectPayment(User user, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        List<PayMethod> payMethods = payMethodRepository.findByUser(user);
        if (payMethods.isEmpty()) {
            throw new IllegalStateException("결제수단이 없습니다.");
        }
        PayMethod payMethod = payMethods.get(0);

        String orderNo = "ORD-" + System.currentTimeMillis();

        // 바로구매용 결제이력 저장
        payHistoryService.saveDirectPayHistory(user, payMethod, product, quantity, orderNo);

        product.setIsSold(true);
        notificationService.createNotification(
                product.getUser(),
                product.getTitle() + " 책이 판매 완료되었습니다!"
        );

        return new OrderResultDTO(orderNo, quantity, product.getPrice() * quantity);
    }

}
