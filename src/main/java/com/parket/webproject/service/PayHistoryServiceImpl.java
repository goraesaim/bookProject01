package com.parket.webproject.service;

import com.parket.webproject.domain.*;
import com.parket.webproject.repository.PayHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PayHistoryServiceImpl implements PayHistoryService {

    final PayHistoryRepository payHistoryRepository;

    @Override
    @Transactional
    public void savePayHistory(User user, PayMethod payMethod, List<Cart> selectedItems, String orderNo) {

        // 주문번호 생성
//        String orderNo = "ORD-" + System.currentTimeMillis();

        for (Cart cart : selectedItems) {
            PayHistory history = PayHistory.builder()
                    .orderNo(orderNo)
                    .user(user)
                    .product(cart.getProduct())
                    .created_at(LocalDateTime.now())
                    .payMethod(payMethod)
                    .build();
            payHistoryRepository.save(history);
        }
    }
}
