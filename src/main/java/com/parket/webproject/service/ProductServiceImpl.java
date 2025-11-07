package com.parket.webproject.service;

import com.parket.webproject.domain.Product;
import com.parket.webproject.domain.User;
import com.parket.webproject.dto.ProductDTO;
import com.parket.webproject.repository.BookRepository;
import com.parket.webproject.repository.member.MemberRepository;
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
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Long insertProduct(ProductDTO productDTO) {
        Product product = dtoToEntity(productDTO);
        User user = memberRepository.findByUsername(productDTO.getUsername());
        product.setUser(user);
        Product saved = productRepository.save(product);
        return saved.getProductId();
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

    @Override
    public ProductDTO findProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        ProductDTO dto = entityToDto(product);
        dto.setUsername(product.getUser().getUsername());
        return dto;
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getProductId()).orElse(null);
        product.change(productDTO.getTitle(), productDTO.getPrice(), productDTO.getAuthor(), productDTO.getConditions(), productDTO.getPublisher());
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        productRepository.delete(product);
    }
}
