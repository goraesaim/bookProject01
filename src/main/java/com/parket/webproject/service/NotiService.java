package com.parket.webproject.service;

import com.parket.webproject.domain.Noti;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotiService {
    List<Noti> getAll();
    Noti getById(Integer idx);
    Noti save(Noti noti, MultipartFile file) throws Exception;
    Noti update(Integer idx, Noti noti, MultipartFile file) throws Exception;
    void delete(Integer idx);
    void incrementCount(Integer idx);

    void deleteFile(Integer idx);

    void updateWithoutFile(Integer idx, Noti noti);
}
