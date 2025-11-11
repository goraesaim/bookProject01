package com.parket.webproject.controller.controller.noti;

import com.parket.webproject.domain.Noti;
import com.parket.webproject.service.NotiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotiController {

    private final NotiService notiService;

    // 목록
    @GetMapping("/list")
    public String list(Model model) {
        List<Noti> noticeList = notiService.getAll();
        if (noticeList == null) noticeList = new ArrayList<>();
        model.addAttribute("noticeList", noticeList);
        return "noti/list";
    }

    // 상세
    @GetMapping("/detail/{idx}")
    public String detail(@PathVariable Integer idx, Model model) {
        Noti board = notiService.getById(idx);
        if (board == null) {
            return "redirect:/noti/list";
        }
        notiService.incrementCount(idx);
        model.addAttribute("board", board);

        // 관리자 여부
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null &&
                auth.isAuthenticated() &&
                auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        return "noti/detail";
    }

    // 작성 페이지
    @GetMapping("/noticeAdd")
    public String addPage() {
        return "noti/noticeAdd";
    }

    // 작성 처리
    @PostMapping("/noticeAdd")
    public String add(@ModelAttribute Noti noti,
                      @RequestParam("file") MultipartFile file,
                      @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        noti.setMemID(userDetails.getUsername());
        noti.setWriter(userDetails.getUsername());
        notiService.save(noti, file);
        return "redirect:/noti/list";
    }

    // 수정 페이지
    @GetMapping("/noticeModify/{idx}")
    @PreAuthorize("hasRole('ADMIN')")
    public String modifyPage(@PathVariable Integer idx, Model model) {
        Noti board = notiService.getById(idx);

        // board가 null인 경우 안전 처리
        if (board == null) {
            board = new Noti();
            board.setIdx(idx);
            board.setTitle("테스트 제목");
            board.setContent("테스트 내용");
        }

        model.addAttribute("board", board);
        return "noti/noticeModify";
    }

    // 수정 처리 (파일 삭제/업로드 통합)
    @PostMapping("/noticeModify/{idx}")
    @PreAuthorize("hasRole('ADMIN')")
    public String modify(@PathVariable Integer idx,
                         @ModelAttribute Noti noti,
                         @RequestParam(value = "file", required = false) MultipartFile file,
                         @RequestParam(value = "deleteFile", required = false) String deleteFile) throws Exception {

        // 기존 파일 삭제 요청 처리
        if ("true".equals(deleteFile)) {
            notiService.deleteFile(idx); // 서비스에 파일 삭제 메서드 필요
            noti.setSfile(null);
            noti.setOfile(null);
        }

        // 새 파일 업로드가 있으면 처리
        if (file != null && !file.isEmpty()) {
            notiService.update(idx, noti, file);
        } else {
            // 파일 없이 제목/내용만 수정
            notiService.updateWithoutFile(idx, noti); // 서비스에 구현 필요
        }

        return "redirect:/noti/detail/" + idx;
    }

    // 삭제
    @GetMapping("/delete/{idx}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Integer idx) {
        notiService.delete(idx);
        return "redirect:/noti/list";
    }
}
