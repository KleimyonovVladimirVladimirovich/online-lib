package com.kleim.online_library.models.author;

import com.kleim.online_library.models.book.BookConverter;
import com.kleim.online_library.models.book.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {

    @Autowired
    private  BookConverter bookConverter;

    public Author toAuthor(AuthorDTO author) {
        return new Author(
                author.id(),
                author.name(),
                author.birthYear(),
                author.bookList().stream().map(bookConverter::toBook).toList()
        );
    }

    public AuthorDTO toDtoAuthor(Author author) {
        return new AuthorDTO(
                author.id(),
                author.name(),
                author.birthYear(),
                author.bookList().stream().map(bookConverter::toBookDTO).toList()
        );
    }
}
