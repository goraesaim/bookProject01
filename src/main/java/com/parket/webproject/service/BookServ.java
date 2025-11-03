package com.parket.webproject.service;

import com.parket.webproject.dto.BookDTO;

import java.io.IOException;
import java.util.List;

public interface BookServ {
    void CrawlBooks() throws IOException;
    List<BookDTO> CrawlSelectBook(String selectText) throws IOException;

}
