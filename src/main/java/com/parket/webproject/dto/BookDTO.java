package com.parket.webproject.dto;

//import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long bno;
    private String title;
    private String author;
    private String price;
    private String imageUrl;

}
