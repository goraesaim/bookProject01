package com.parket.webproject.repository.cart;

import com.parket.webproject.domain.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT c FROM Cart c JOIN FETCH c.product WHERE c.userId = :userId")
    List<Cart> findByUserId(int userId);

    @Transactional
    void deleteByUserId(int userId);
}