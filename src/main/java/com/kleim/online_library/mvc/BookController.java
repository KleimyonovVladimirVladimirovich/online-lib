package com.kleim.online_library.mvc;

import com.kleim.online_library.models.book.BookConverter;
import com.kleim.online_library.models.book.BookDTO;
import com.kleim.online_library.models.book.BookSearchFilter;
import com.kleim.online_library.service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/books")
@RestController
public class BookController {

    private static Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    private final BookConverter bookConverter;


    public BookController(BookService bookService, BookConverter bookConverter) {
        this.bookService = bookService;
        this.bookConverter = bookConverter;
    }


    @PostMapping
    public ResponseEntity<BookDTO> createBook(
            @RequestBody @Valid BookDTO bookToCreate
    ) {
        logger.info("Get request for create books");
        var createdBook = bookService.createBook(bookConverter.toBook(bookToCreate));

        return ResponseEntity.status(HttpStatus.CREATED).body(bookConverter.toBookDTO(createdBook));
    }


    @GetMapping()
    public List<BookDTO> getAllBooks(
//            @RequestParam(value = "authorName",required = false) String authorName,
//            @RequestParam(value = "cost", required = false) Integer cost
         @Valid  BookSearchFilter bookSearchFilter
    ){
        logger.info("Get request for all get books");
        return bookService.searchAllBooks(bookSearchFilter)
                .stream()
                .map(bookConverter::toBookDTO)
                .toList();
    }


    @GetMapping("/{id}")
    public BookDTO findById(
            @PathVariable(value = "id", required = false) Long id
    ) {
        logger.info("Get request for find id get books");
        return bookConverter.toBookDTO(bookService.findById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByIdBook(
            @PathVariable("id") Long id
    ) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable("id") Long id,
            @RequestBody @Valid BookDTO updateToBook
    ) {
        var updatedBook = bookService.updateBook(id, bookConverter.toBook(updateToBook));
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(bookConverter.toBookDTO(updatedBook));
    }




}

