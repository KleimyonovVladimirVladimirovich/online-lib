package com.kleim.online_library.mvc;


import com.kleim.online_library.models.author.Author;
import com.kleim.online_library.models.author.AuthorConverter;
import com.kleim.online_library.models.author.AuthorDTO;
import com.kleim.online_library.service.AuthorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/author")
@RestController
public class AuthorController {
    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService authorService;
    private final AuthorConverter dtoConverter;

    public AuthorController(
            AuthorService authorService,
            AuthorConverter dtoConverter
    ) {
        this.authorService = authorService;
        this.dtoConverter = dtoConverter;
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(
            @RequestBody @Valid AuthorDTO authorToCreate
    ) {
        log.info("Get request for create author: author={}", authorToCreate);
        Author createdAuthor = authorService.createAuthor(
                dtoConverter.toAuthor(authorToCreate));


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dtoConverter.toDtoAuthor(createdAuthor));
    }

    @GetMapping
    public List<AuthorDTO> getAllAuthors() {
        log.info("Get request for get all authors");
        return authorService.getAllAuthors()
                .stream()
                .map(dtoConverter::toDtoAuthor)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(
            @PathVariable("id") Long authorId
    ) {
        log.info("Get request for delete author: id={}", authorId);
        authorService.deleteAuthor(authorId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
