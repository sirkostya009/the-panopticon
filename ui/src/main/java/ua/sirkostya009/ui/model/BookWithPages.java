package ua.sirkostya009.ui.model;

import java.util.List;
import java.util.Set;

public record BookWithPages(
        String id,
        Set<String> authors,
        Set<String> genres,
        List<String> pages,
        Integer pagesCount,
        Integer wordsCount
) {
}
