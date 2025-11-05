package com.parket.webproject.service;

import com.parket.webproject.domain.Product;
import com.parket.webproject.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    Long insertProduct(ProductDTO productDTO);
    List<ProductDTO> findAllProducts();
//    ProductDTO findProductById(Long id, Integer mode);
//    void updateProduct(ProductDTO productDTO);
//    void deleteProduct(Long id);

    default Product dtoToEntity(ProductDTO productDTO) {
        Product product = Product.builder()
                .productId(productDTO.getProductId())
                .title(productDTO.getTitle())
                .content(productDTO.getContent())
                .price(productDTO.getPrice())
                .author(productDTO.getAuthor())
                .publisher(productDTO.getPublisher())
                .conditions(productDTO.getConditions())
                .build();
        return product;
    }

    default ProductDTO entityToDto(Product product) {
        ProductDTO productDTO = ProductDTO.builder()
                .productId(product.getProductId())
                .title(product.getTitle())
//                .content(product.getContent())
                .price(product.getPrice())
                .author(product.getAuthor())
//                .publisher(product.getPublisher())
                .conditions(product.getConditions())
//                .readcount(product.getReadcount())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
        return productDTO;
    }
}
