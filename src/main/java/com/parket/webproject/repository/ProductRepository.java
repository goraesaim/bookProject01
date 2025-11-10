package com.parket.webproject.repository;

import com.parket.webproject.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
=======
import java.util.List;

@Repository
>>>>>>> 400af16a2bdaecdf19a6652e0398320a1aab5f3c
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUserId(Long userId);
}
