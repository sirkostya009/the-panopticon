package ua.sirkostya009.library.dto;

import ua.sirkostya009.library.model.Book;

import java.util.Set;

public record PageDto(
        String id,
        String title,
        Set<String> authors,
        Set<String> genres,
        String pageContent,
        Integer pageNo,
        Integer pagesCount,
        Integer wordsCount
) {
    public static PageDto of(Book book, Integer page) {
        return new PageDto(
                book.getId(),
                book.getTitle(),
                book.getAuthors(),
                book.getGenres(),
                book.getPages().get(page),
                page,
                book.getPagesCount(),
                book.getWordsCount()
        );
    }
}
