package com.parket.webproject.service;

import com.parket.webproject.domain.Product;
import com.parket.webproject.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    Long insertProduct(ProductDTO productDTO);
    List<ProductDTO> findAllProducts();
<<<<<<< HEAD
//    ProductDTO findProductById(Long id, Integer mode);
//    void updateProduct(ProductDTO productDTO);
//    void deleteProduct(Long id);
=======
    List<ProductDTO> findProductsByUserId(Long userId);
    ProductDTO findProductById(Long productId);
    void updateProduct(ProductDTO productDTO);
    void deleteProduct(Long productId);
>>>>>>> 400af16a2bdaecdf19a6652e0398320a1aab5f3c

    default Product dtoToEntity(ProductDTO productDTO) {
        Product product = Product.builder()
                .productId(productDTO.getProductId())
                .title(productDTO.getTitle())
                .price(productDTO.getPrice())
                .author(productDTO.getAuthor())
                .publisher(productDTO.getPublisher())
                .conditions(productDTO.getConditions())
                .isSold(productDTO.getIsSold() != null ? productDTO.getIsSold() : false)
                .build();
        return product;
    }

    default ProductDTO entityToDto(Product product) {
        ProductDTO productDTO = ProductDTO.builder()
                .productId(product.getProductId())
                .title(product.getTitle())
                .price(product.getPrice())
                .author(product.getAuthor())
                .publisher(product.getPublisher())
                .conditions(product.getConditions())
                .isSold(product.getIsSold())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
        return productDTO;
    }
}
