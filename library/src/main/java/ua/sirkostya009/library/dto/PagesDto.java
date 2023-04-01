package ua.sirkostya009.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ua.sirkostya009.library.model.Book;

import java.util.List;
import java.util.Set;

public record PagesDto(
        String id,
        @NotNull
        @NotBlank
        String title,
        @NotNull
        Set<String> authors,
        @NotNull
        Set<String> genres,
        @NotNull
        List<String> pages,
        Integer pagesCount,
        Integer wordsCount
) {
    public static PagesDto of(Book book) {
        return new PagesDto(
                book.getId(),
                book.getTitle(),
                book.getAuthors(),
                book.getGenres(),
                book.getPages(),
                book.getPagesCount(),
                book.getWordsCount()
        );
    }
}
