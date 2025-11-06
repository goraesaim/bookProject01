package com.parket.webproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.dto.ProductDTO;
import com.parket.webproject.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/write")
    public void registerGet() {
        log.info("상품 등록 페이지 진입");

    }

    @PostMapping("/write")
    public String registerPost(@RequestParam(required = false) List<String> conditions, ProductDTO productDTO, @AuthenticationPrincipal PrincipalDetails principal) {
        if (conditions != null && !conditions.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(conditions);
                productDTO.setConditions(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        productDTO.setUsername(principal.getUsername());
        Long productId = productService.insertProduct(productDTO);
        log.info("상품 등록 완료: productId=" + productId);
        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public void list(Model model) {
        List<ProductDTO> products = productService.findAllProducts();
        model.addAttribute("products", products);
    }

    @GetMapping( "/modify")
    public void readProduct(@RequestParam("productId") Long productId, Model model) {
        ProductDTO productDTO = productService.findProductById(productId);
        model.addAttribute("product", productDTO);
    }

    // 상품 수정 처리
    @PostMapping("/modify")
    public String modifyProduct(ProductDTO productDTO) {
        productService.updateProduct(productDTO);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return isAdmin ? "redirect:/product/list" : "redirect:/mypage/writeList";
    }

    // 상품 삭제 처리
    @GetMapping("/remove")
    public String removeProduct(@RequestParam("productId") Long productId) {
        productService.deleteProduct(productId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return isAdmin ? "redirect:/product/list" : "redirect:/mypage/writeList";
    }

}