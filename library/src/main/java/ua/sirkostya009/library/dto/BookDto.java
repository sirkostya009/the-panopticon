package ua.sirkostya009.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ua.sirkostya009.library.model.Book;

import java.util.Set;

public record BookDto(
        String id,
        @NotNull
        @NotBlank
        String title,
        @NotNull
        Set<String> authors,
        @NotNull
        Set<String> genres,
        @NotNull
        Integer pagesCount,
        @NotNull
        Integer wordsCount
) {
    public static BookDto of(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthors(),
                book.getGenres(),
                book.getPagesCount(),
                book.getWordsCount()
        );
    }
}
