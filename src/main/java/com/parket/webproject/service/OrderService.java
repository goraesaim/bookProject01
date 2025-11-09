package com.parket.webproject.service;

import com.parket.webproject.domain.User;
import com.parket.webproject.dto.OrderResultDTO;

import java.util.List;

public interface OrderService {
    OrderResultDTO completePayment(User user, List<Integer> cardIds);
}
