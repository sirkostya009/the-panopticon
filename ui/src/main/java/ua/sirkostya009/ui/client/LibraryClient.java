package ua.sirkostya009.ui.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ua.sirkostya009.ui.model.Book;
import ua.sirkostya009.ui.model.BookWithPages;
import org.springframework.data.domain.Page;

@FeignClient(name = "library", url = "${backend.urls.library-client}")
public interface LibraryClient {
    @GetMapping("/")
    Page<Book> get(@RequestParam(value = "page", required = false) int page,
                   @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping("/{id}")
    Object id(@PathVariable String id,
              @RequestParam(value = "page", required = false) int page,
              @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping("/buy/{id}")
    void buy(@PathVariable String id,
             @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping("/bought")
    Page<Book> bought(@RequestParam(value = "page", required = false) int page,
                      @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping("/download/{id}")
    BookWithPages download(@PathVariable String id,
                           @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
