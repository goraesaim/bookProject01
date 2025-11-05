package com.parket.webproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_test")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pd_test {
    @Id
    private int productId;
    private String title;
    private int price;
}
