package com.parket.webproject.service;

import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.PayMethod;
import com.parket.webproject.domain.User;
import com.parket.webproject.repository.PayMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PayMethodServiceImpl implements PayMethodService {

    private final PayMethodRepository payMethodRepository;
    
    //결제 수단 등록 및 수정!!!!!!!!!!!!!
    @Override
    public PayMethod saveOrUpdatePayMethod(User user, PayMethod form) {
        Optional<PayMethod> existing = payMethodRepository.findMethodByUser(user);

        PayMethod target;

        if (existing.isPresent()) {
            // 수정 모드
            target = existing.get();
            target.setMethodType(form.getMethodType());
            target.setBankName(form.getBankName());
            target.setAccountNumber(form.getAccountNumber());
        } else {
            // 등록 모드
            form.setUser(user);
            target = form;
        }
        return payMethodRepository.save(target);
    }

    // 결제하기 버튼 - 결제수단 있는지 없는지 확인
    @Override
    public boolean hasPayMethod(User user) {
        return payMethodRepository.existsByUser(user);
    }

    @Override
    public Optional<PayMethod> getPayMethodByUser(User user) {
        return payMethodRepository.findMethodByUser(user);
    }


}
