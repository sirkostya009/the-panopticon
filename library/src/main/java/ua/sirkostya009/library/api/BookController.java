package ua.sirkostya009.library.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.sirkostya009.library.annotation.UserId;
import ua.sirkostya009.library.dto.BookDto;
import ua.sirkostya009.library.dto.PagesDto;
import ua.sirkostya009.library.service.BookService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books/")
public class BookController {
    private final BookService service;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_VIEW_BOOKS')")
    public Page<BookDto> get(@RequestParam(value = "page", defaultValue = "0") int page) {
        return service.findAll(page).map(BookDto::of);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_VIEW_BOOKS')")
    public Object id(@PathVariable String id,
                     @RequestParam(value = "page", required = false) Integer page) {
        return page == null
                ? BookDto.of(service.findById(id))
                : service.findBookPage(id, page);
    }

    @GetMapping("/buy/{id}")
    @PreAuthorize("hasAuthority('SCOPE_VIEW_BOOKS')")
    public void buy(@PathVariable String id,
                    @UserId String uid) {
        service.buyBook(id, uid);
    }

    @GetMapping("/bought")
    @PreAuthorize("hasAuthority('SCOPE_VIEW_BOOKS')")
    public Page<BookDto> bought(@RequestParam(value = "page", defaultValue = "0") int page,
                                @UserId String uid) {
        return service.findAllBoughtBy(uid, page).map(BookDto::of);
    }

    @GetMapping("/download/{id}")
    @PreAuthorize("hasAuthority('SCOPE_VIEW_BOOKS')")
    public PagesDto download(@PathVariable String id,
                             @UserId String uid) {
        return PagesDto.of(service.download(id, uid));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADD_BOOKS')")
    public BookDto add(@RequestBody @Valid PagesDto dto) {
        return BookDto.of(service.add(dto));
    }
}
