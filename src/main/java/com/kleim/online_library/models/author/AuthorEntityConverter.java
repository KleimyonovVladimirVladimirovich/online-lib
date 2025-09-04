package com.kleim.online_library.models.author;

import com.kleim.online_library.entity.AuthorEntity;
import com.kleim.online_library.models.book.BookConverter;
import com.kleim.online_library.models.book.BookEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorEntityConverter {

    @Autowired
    private final BookEntityConverter bookEntityConverter;

    public AuthorEntityConverter(BookEntityConverter bookEntityConverter) {
        this.bookEntityConverter = bookEntityConverter;
    }

    public Author toAuthorFromEntity(AuthorEntity author) {
        return new Author(
                author.getId(),
                author.getName(),
                author.getBirthYear(),
                author.getBookList().stream().map(bookEntityConverter::toBookFromEntity).toList()
        );
    }

    public AuthorEntity toAuthorEntity(Author author) {
        return new AuthorEntity(
                author.id(),
                author.name(),
                author.birthYear(),
                author.bookList().stream().map(bookEntityConverter::toBookEntity).collect(Collectors.toSet())
        );
    }

}
