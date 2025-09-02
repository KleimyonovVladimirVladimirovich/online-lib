package com.kleim.online_library.models.author;

import com.kleim.online_library.models.book.Book;
import com.kleim.online_library.models.book.BookDTO;
import jakarta.validation.constraints.Null;

import java.util.List;

public record AuthorDTO (
        @Null
        Long id,
        String name,
        Integer birthYear,
        List<BookDTO> bookList

) { }
