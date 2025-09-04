package com.kleim.online_library.models.author;

import com.kleim.online_library.models.book.Book;
import com.kleim.online_library.models.book.BookDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AuthorDTO (

        @Null
        Long id,
        @NotBlank
        String name,
        @Min(0)
        Integer birthYear,

        @Size(max = 0)
        List<BookDTO> bookList

) { }
