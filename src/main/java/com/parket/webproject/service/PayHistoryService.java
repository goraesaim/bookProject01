package com.parket.webproject.service;

import com.parket.webproject.domain.*;

import java.util.List;

public interface PayHistoryService {
    void savePayHistory(User user, PayMethod payMethod, List<Cart> selectedItems, String orderNo);
}
