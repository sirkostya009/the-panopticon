package ua.sirkostya009.ui.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record Book(
        String id,
        String title,
        Set<String> authors,
        Set<String> genres,
        Integer pagesCount,
        Integer wordsCount
) {
    public static Book from(Map<String, ?> map) {
        return new Book(
                (String) map.get("id"),
                (String) map.get("title"),
                new HashSet<>((List<String>) map.get("authors")),
                new HashSet<>((List<String>) map.get("genres")),
                (Integer) map.get("pagesCount"),
                (Integer) map.get("wordsCount")
        );
    }
}
