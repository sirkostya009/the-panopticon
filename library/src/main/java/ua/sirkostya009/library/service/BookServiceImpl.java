package ua.sirkostya009.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.sirkostya009.library.dto.BookDto;
import ua.sirkostya009.library.dto.PageDto;
import ua.sirkostya009.library.dto.PagesDto;
import ua.sirkostya009.library.exception.ForbiddenException;
import ua.sirkostya009.library.exception.NotFoundException;
import ua.sirkostya009.library.model.Book;
import ua.sirkostya009.library.repository.BookRepository;

import java.util.Arrays;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final static int BOOKS_PER_PAGE = 20;

    private final BookRepository repository;

    @Override
    public Page<Book> findAll(int page) {
        return repository.findAll(pageRequest(page));
    }

    @Override
    public Book findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));
    }

    @Override
    public Object bookOrPage(String id, Integer page, String uid) {
        var book = findById(id);

        if (page != null) {
            if (book.getBoughtBy().contains(uid)) {
                return PageDto.of(book, page);
            } else {
                throw new ForbiddenException("Buy book to view");
            }
        }

        return BookDto.of(book);
    }

    @Override
    public void buyBook(String bookId, String userId) {
        repository.addBuyer(bookId, userId);
    }

    @Override
    public Page<Book> findAllBoughtBy(String uid, int page) {
        return repository.findBoughtBy(uid, pageRequest(page));
    }

    @Override
    public Book download(String bookId, String userId) {
        var book = findById(bookId);

        if (!book.getBoughtBy().contains(userId))
            throw new ForbiddenException("Access to the book denied");

        return book;
    }

    @Override
    public Book add(PagesDto dto) {
        return repository.save(Book.builder()
                        .authors(dto.authors())
                        .genres(dto.genres())
                        .pages(dto.pages())
                        .pagesCount(dto.pagesCount() == null ? dto.pages().size() : dto.pagesCount())
                        .wordsCount(dto.wordsCount() == null
                                    ? (int) dto.pages().stream().flatMap(string -> Arrays.stream(string.split(" "))).count()
                                    : dto.wordsCount())
                        .boughtBy(Set.of())
                        .build());
    }

    private PageRequest pageRequest(int page) {
        return PageRequest.of(page, BOOKS_PER_PAGE);
    }
}
