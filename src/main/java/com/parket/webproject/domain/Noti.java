package com.parket.webproject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "noti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Noti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    private String memID;      // 작성자 ID
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String writer;     // 작성자 이름
    private LocalDate indate;  // 작성일
    private Integer count;     // 조회수

    private String ofile;      // 원본 파일
    private String sfile;      // 서버 저장 파일명
}
