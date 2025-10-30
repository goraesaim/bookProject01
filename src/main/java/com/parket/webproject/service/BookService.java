package com.parket.webproject.service;


import com.parket.webproject.domain.CrawlBook;
import com.parket.webproject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BookService implements BookServ{
    private final BookRepository bookRepository;

    @Override
    public void CrawlBooks() throws IOException {
        String url = "https://www.aladin.co.kr/shop/common/wbest.aspx?BestType=Bestseller";

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .get();

        Elements bookElements = doc.select(".ss_book_box");
        for (Element book : bookElements) {

            Element img = book.selectFirst(".front_cover");
            String imageUrl = img != null ? img.attr("src") : null;
            String title = book.select(".bo3").text();
            Elements authors = book.select("ul > li:nth-child(3) > a");
            String author="";
            for (Element a : authors) {
                author += a.text()+", ";
            }
//            List<String> author = new ArrayList<>();
//            for (Element a : authors) {
//                author.add(a.text());
//            }
            String price = book.select(".ss_p2").text();




            System.out.println("이미지 : " + imageUrl);
            System.out.println("제목 : " + title);
            System.out.println("저자 : " + author);
            System.out.println("가격 : " + price);
            System.out.println("------------------");


            if (!title.isEmpty() && !author.isEmpty() && !price.isEmpty()) {
                CrawlBook crawlBook = new CrawlBook(null, title, author, price, imageUrl);
                bookRepository.save(crawlBook);
            }

        }
    }
}
