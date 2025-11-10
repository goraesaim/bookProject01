package com.parket.webproject.domain;


import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;

@Entity
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class CrawlBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    private String title;
    private String author;
    private String price;
    private String publisher;
    private String category;
    @Column(length = 1000)
    private String bookImage;
    private String productUrl;
}
