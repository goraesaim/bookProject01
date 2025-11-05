package com.parket.webproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString(exclude = {"user", "crawlBook"})
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    // 이미지 url, 카테고리,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

//    User 테이블과 연관된 외래키
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private User user;

    @Column(nullable = false, length = 500)
    private String title;
    private String content;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false, length = 100)
    private String author;
    @Column(nullable = false, length = 100)
    private String publisher;
//    제품 상태 필드
    @Column(columnDefinition = "JSON")
    private String conditions;
//    제품 판매 여부
    @Column(nullable = false)
    private Boolean isSold = false;

    @ColumnDefault(value = "0")
    private int readcount;

//    CrawlBook 테이블과 연관된 외래키
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "crawlBookId")
//    private CrawlBook crawlBook;

    public void updateReadcount() {
        readcount = readcount + 1;
    }
//    public void change(String title, Long price, String author, String conditions) {
//        this.title = title;
////        this.content = content;
//        this.price = price;
//        this.author = author;
////        this.publisher = publisher;
//        this.conditions = conditions;
//    }
}
