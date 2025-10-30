package com.parket.webproject.repository;

import com.parket.webproject.domain.CrawlBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<CrawlBook, Long> {
//    Object findAll();
}
