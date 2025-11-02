package com.parket.webproject.service;


import com.parket.webproject.domain.CrawlBook;
import com.parket.webproject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService implements BookServ{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void CrawlBooks() throws IOException{
        try{
            crawlAladin("https://www.aladin.co.kr/shop/common/wbest.aspx?BestType=Bestseller", "국베");
            Thread.sleep(1000);
            crawlAladin("https://www.aladin.co.kr/shop/common/wnew.aspx?BranchType=1", "신간");
            Thread.sleep(1000);
            crawlAladin("https://www.aladin.co.kr/shop/common/wbest.aspx?BranchType=7", "외베");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void crawlAladin(String url, String cate) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .get();
        Elements bookElements = doc.select(".ss_book_box");
        System.out.println(bookElements.size());
        for (Element book : bookElements) {
            Element img = book.selectFirst(".front_cover, .i_cover");
            String imageUrl = img != null ? img.attr("src") : null;
            String title = book.select(".bo3").text();
            String[] result=TakeAuthor(book);
            String author = result[0];
            String publisher = result[1];
            String category = cate;
            String price = book.select(".ss_p2").text();
            if (!title.isEmpty()) {
                CrawlBook crawlBook = new CrawlBook(null, title, author, price, publisher,category,imageUrl);
                bookRepository.count();
                saveOrUpdateBook(crawlBook);
            }
        }
    }
    public static String[] TakeAuthor(Element book){
        String author="";
        Elements authors=null;
        Elements liList = book.select("ul");
        boolean hasTrash = false;
        for (Element li : liList) {
            if (!li.select(".ss_ht1").isEmpty()) {
                hasTrash = true;
                break;
            }
        }
        if (hasTrash) {
            authors = liList.select("li:nth-child(3) > a");
        } else {
            authors = liList.select("li:nth-child(2) > a");
        }
        String publisher="";
        int size = authors.size();
        if (hasTrash) {
            if (size >= 2) {
                for (int i = 0; i < size - 1; i++) {
                    author += authors.get(i).text();
                    if (i < size - 2) author += ", ";
                }
                publisher = authors.get(size - 1).text();
            } else if (size == 1) {
                author = authors.get(0).text();
                publisher = "";
            }
        }else {
            if (size >= 3) {
                for (int i = 0; i < size - 2; i++) {
                    author += authors.get(i).text();
                    if (i < size - 3) author += ", ";
                }
                publisher = authors.get(size - 2).text();
            } else if (size == 2) {
                author = authors.get(0).text();
                publisher = authors.get(1).text();
            } else if (size == 1) {
                author = authors.get(0).text();
                publisher = "";
            }
        }
        if (publisher.equals("마이리스트")) {
            publisher = author;
            author="저자 정보 없음";
        }
        return new String[] { author, publisher };
    }

    public void saveOrUpdateBook(CrawlBook newBook) {
        Optional<CrawlBook> existing = bookRepository.findByTitleAndAuthor(
                newBook.getTitle(), newBook.getAuthor()
        );

        if (existing.isPresent()) {
            CrawlBook book = existing.get();
            Set<String> categories = new HashSet<>(Arrays.asList(book.getCategory().split(",")));
            categories.add(newBook.getCategory());
            book.setCategory(String.join(",", categories));
            bookRepository.save(book);
        } else {
            bookRepository.save(newBook);
        }
    }

}
