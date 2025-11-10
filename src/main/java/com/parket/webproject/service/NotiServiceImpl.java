package com.parket.webproject.service;

import com.parket.webproject.domain.Noti;
import com.parket.webproject.repository.NotiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotiServiceImpl implements NotiService {

    private final NotiRepository notiRepository;
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/"; // 서버 저장 경로

    // 업로드 폴더 없으면 생성
    private void createUploadDirIfNotExists() {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public List<Noti> getAll() {
        return notiRepository.findAll();
    }

    @Override
    public Noti getById(Integer idx) {
        return notiRepository.findById(idx).orElseThrow();
    }

    @Override
    public Noti save(Noti noti, MultipartFile file) throws Exception {
        createUploadDirIfNotExists(); // 업로드 폴더 확인

        if (file != null && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            String savedFileName = System.currentTimeMillis() + "_" + originalFileName;
            File dest = new File(uploadDir + savedFileName);
            file.transferTo(dest);
            noti.setOfile(originalFileName);
            noti.setSfile(savedFileName);
        }

        noti.setIndate(LocalDate.now());
        noti.setCount(0);

        return notiRepository.save(noti);
    }

    @Override
    public Noti update(Integer idx, Noti notiData, MultipartFile file) throws Exception {
        Noti noti = getById(idx);
        noti.setTitle(notiData.getTitle());
        noti.setContent(notiData.getContent());

        createUploadDirIfNotExists(); // 업로드 폴더 확인

        if (file != null && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            String savedFileName = System.currentTimeMillis() + "_" + originalFileName;
            File dest = new File(uploadDir + savedFileName);
            file.transferTo(dest);
            noti.setOfile(originalFileName);
            noti.setSfile(savedFileName);
        }

        return notiRepository.save(noti);
    }

    @Override
    public void delete(Integer idx) {
        notiRepository.deleteById(idx);
    }

    @Override
    public void incrementCount(Integer idx) {
        Noti noti = getById(idx);
        noti.setCount(noti.getCount() + 1);
        notiRepository.save(noti);
    }

    @Override
    public void deleteFile(Integer idx) {
        Noti board = notiRepository.findById(idx).orElseThrow();
        if (board.getSfile() != null) {
            try {
                Path path = Paths.get(uploadDir, board.getSfile());
                Files.deleteIfExists(path);
                board.setSfile(null);
                board.setOfile(null);
                notiRepository.save(board);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void updateWithoutFile(Integer idx, Noti updatedNoti) {
        Noti board = notiRepository.findById(idx).orElseThrow();
        board.setTitle(updatedNoti.getTitle());
        board.setContent(updatedNoti.getContent());
        notiRepository.save(board);
    }
}
