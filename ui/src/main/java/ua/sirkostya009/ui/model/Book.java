package ua.sirkostya009.ui.model;

import java.util.Set;

public record Book(
        String id,
        String title,
        String model,
        Set<String> authors,
        Set<String> genres,
        Integer pagesCount,
        Integer wordsCount
) {
}
