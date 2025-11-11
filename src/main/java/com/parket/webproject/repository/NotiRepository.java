package com.parket.webproject.repository;

import com.parket.webproject.domain.Noti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotiRepository extends JpaRepository<Noti, Integer> {
}
