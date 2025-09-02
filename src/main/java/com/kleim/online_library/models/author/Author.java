package com.kleim.online_library.models.author;

import com.kleim.online_library.models.book.Book;
import com.kleim.online_library.models.book.BookDTO;

import java.util.List;

public record Author(

        Long id,
        String name,
        Integer birthYear,
        List<Book> bookList



) { }
