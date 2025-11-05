package com.parket.webproject.dto;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDTO {
    private int cartId;
    private int userId;
    private int productId;
    private int quantity;
    private Timestamp addedAt;
}
