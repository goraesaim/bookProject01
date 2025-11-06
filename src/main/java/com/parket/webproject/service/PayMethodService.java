package com.parket.webproject.service;

import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;

public interface PayMethodService {
    String registerPayMethod(PayMethod payMethod);
    boolean hasPayMethod(User user);
}
