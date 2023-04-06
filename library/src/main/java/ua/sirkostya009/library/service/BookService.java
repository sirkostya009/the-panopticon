package ua.sirkostya009.library.service;

import org.springframework.data.domain.Page;
import ua.sirkostya009.library.dto.PagesDto;
import ua.sirkostya009.library.model.Book;

public interface BookService {
    Page<Book> findAll(int page);

    Book findById(String id);

    Object bookOrPage(String id, Integer page, String uid);

    void buyBook(String bookId, String userId);

    Page<Book> findAllBoughtBy(String id, int page);

    Book download(String bookId, String userId);

    Book add(PagesDto dto);
}
