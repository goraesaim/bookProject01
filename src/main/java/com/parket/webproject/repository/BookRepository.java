package com.parket.webproject.repository;

import com.parket.webproject.domain.CrawlBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<CrawlBook, Long> {
//    Object findAll();
    Optional<CrawlBook> findByTitleAndAuthor(String title, String author);


    @Query("SELECT b FROM CrawlBook b WHERE b.category LIKE :keyword ORDER BY b.bno ASC")
    List<CrawlBook> findByCategoryContaining(@Param("keyword") String keyword);

}
