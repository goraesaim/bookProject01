package com.parket.webproject.service;

import com.parket.webproject.domain.Product;
import com.parket.webproject.dto.ProductDTO;
import com.parket.webproject.repository.BookRepository;
import com.parket.webproject.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Long insertProduct(ProductDTO productDTO) {
        Product product = dtoToEntity(productDTO);
//        User user = userRepository.findByUsername(productDTO.getUsername());
//        product.setUser(user);

//        Optional<CrawlBook> optionalBook = bookRepository.findByTitleAndAuthor(
//                productDTO.getTitle(), productDTO.getAuthor());
//        if (optionalBook.isPresent()) {
//            product.setCrawlBook(optionalBook.get());
//        }

        Long productId = productRepository.save(product).getProductId();
        return productId;
    }

    @Override
    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> dtos = new ArrayList<>();
        for (Product product : products) {
            dtos.add(entityToDto(product));
        }
        return dtos;
    }
<<<<<<< HEAD
//
//    @Override
//    public ProductDTO findProductById(Long id, Integer mode) {
//        Product product = productRepository.findById(id).orElse(null);
//        if(mode==1){
////            product.updateReadcount();
//            productRepository.save(product);
//        }
//        ProductDTO dto = entityToDto(product);
////        dto.setUsername(product.getUser().getUsername());
//        return dto;
//    }
//
//    @Override
//    public void updateProduct(ProductDTO productDTO) {
//        Product product = productRepository.findById(productDTO.getProductId()).orElse(null);
////        product.change(productDTO.getTitle(), productDTO.getContent(), productDTO.getPrice(), productDTO.getPublisher(), productDTO.getAuthor(), productDTO.getConditions());
//        product.change(productDTO.getTitle(), productDTO.getPrice(), productDTO.getAuthor(), productDTO.getConditions());
//        productRepository.save(product);
//    }
//
//    @Override
//    public void deleteProduct(Long id) {
//        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
//        productRepository.delete(product);
//    }
=======

    @Override
    public List<ProductDTO> findProductsByUserId(Long userId) {
        List<Product> products = productRepository.findByUserId(userId);
        List<ProductDTO> dtos = new ArrayList<>();
        for (Product product : products) {
            dtos.add(entityToDto(product));
        }

        return dtos;
    }

    @Override
    public ProductDTO findProductById(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        ProductDTO dto = entityToDto(product);
        return dto;
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getProductId()).orElse(null);
        product.change(productDTO.getPrice(), productDTO.getConditions());
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        productRepository.delete(product);
    }
>>>>>>> 400af16a2bdaecdf19a6652e0398320a1aab5f3c
}
