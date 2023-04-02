package ua.sirkostya009.ui.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record BookWithPage(
        String id,
        String title,
        Set<String> authors,
        Set<String> genres,
        String pageContent,
        Integer pageNo,
        Integer pagesCount,
        Integer wordsCount
) {
    public static BookWithPage from(Map<String, Object> map) {
        return new BookWithPage(
                (String) map.get("id"),
                (String) map.get("title"),
                new HashSet<>((List<String>) map.get("authors")),
                new HashSet<>((List<String>) map.get("genres")),
                (String) map.get("pageContent"),
                (Integer) map.get("pageNo"),
                (Integer) map.get("pagesCount"),
                (Integer) map.get("wordsCount")
        );
    }
}
