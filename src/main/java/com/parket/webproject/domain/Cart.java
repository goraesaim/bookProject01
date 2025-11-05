package com.parket.webproject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    private int userId;
    //나중에 주석풀요아함
    //private int productId;
    private int quantity;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime addedAt;

    // 나중에 join 수정해야함!!!!!!!!!!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")

    // !!!!!! 나중에 진짜 domain으로 바꿔야함..
    private Pd_test product;

    public Cart(int userId, int quantity) {
        this.userId = userId;
        //this.productId = productId;
        this.quantity = quantity;
    }


}