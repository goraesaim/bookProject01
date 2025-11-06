package com.parket.webproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parket.webproject.dto.ProductDTO;
import com.parket.webproject.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String registerPost(@RequestParam(required = false) List<String> conditions, ProductDTO productDTO) {
        if (conditions != null && !conditions.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(conditions); // JSON 배열로 변환
                productDTO.setConditions(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Long productId = productService.insertProduct(productDTO);
        log.info("상품 등록 완료: productId=" + productId);
        return "redirect:/product/list";
    }

    @PostMapping("/write2")
    public String registerPost2(@RequestParam(required = false) List<String> conditions, ProductDTO productDTO) {
        if (conditions != null && !conditions.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(conditions); // JSON 배열로 변환
                productDTO.setConditions(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Long productId = productService.insertProduct(productDTO);
        log.info("상품 등록 완료: productId=" + productId);
        return "redirect:/product/list2";
    }

    @GetMapping("/list")
    public void list(Model model) {
        List<ProductDTO> products = productService.findAllProducts();
        model.addAttribute("products", products);
    }

    @GetMapping("/list2")
    public void list2(Model model) {
        List<ProductDTO> products = productService.findAllProducts();
        model.addAttribute("products", products);
    }
//
//    // 상품 상세 조회 및 수정 페이지
//    @GetMapping({"/read", "/modify"})
//    public void readProduct(@RequestParam("id") Long id,
//                             @RequestParam(value = "mode", defaultValue = "0") Integer mode,
//                             Model model) {
//        ProductDTO productDTO = productService.findProductById(id, mode);
//        model.addAttribute("product", productDTO);
//    }
//
//    // 상품 수정 처리
//    @PostMapping("/modify")
//    public String modifyProduct(ProductDTO productDTO, RedirectAttributes redirectAttributes) {
//        productService.updateProduct(productDTO);
//        redirectAttributes.addAttribute("id", productDTO.getProductId());
//        redirectAttributes.addAttribute("mode", 1);
//        return "redirect:/product/read";
//    }
//
//    // 상품 삭제 처리
//    @GetMapping("/remove")
//    public String removeProduct(@RequestParam("id") Long id) {
//        productService.deleteProduct(id);
//        return "redirect:/product/list";
//    }

}