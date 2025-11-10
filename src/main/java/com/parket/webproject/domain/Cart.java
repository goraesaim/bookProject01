package com.parket.webproject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString(exclude ={"product","user"}) // @ManyToOne
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    // 나중에 join 수정해야함!!!!!!!!!!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    // 나중에 join 수정해야함!!!!!!!!!!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime addedAt;

    @Column(nullable = false)
    private int quantity = 1;

}