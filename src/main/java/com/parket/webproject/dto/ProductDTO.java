package com.parket.webproject.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long productId;
//    @NotEmpty
//    private String username;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private Long price;
    @NotEmpty
    private String author;
//    @NotEmpty
    private String publisher;
    // 제품 상태
    private String conditions;
    // 판매 여부
    private Boolean isSold;
    private int readcount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
