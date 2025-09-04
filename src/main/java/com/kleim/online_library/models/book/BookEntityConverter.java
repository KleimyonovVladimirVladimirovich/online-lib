package com.kleim.online_library.models.book;

import com.kleim.online_library.entity.BookEntity;
import org.springframework.stereotype.Component;


@Component
public class BookEntityConverter {

    public BookEntity toBookEntity(Book book) {
        return new BookEntity(
                book.id(),
                book.name(),
                book.authorId(),
                book.publicationYear(),
                book.pageNumber(),
                book.cost()
        );
    }

    public Book toBookFromEntity(BookEntity book) {
        return new Book(
                book.getId(),
                book.getName(),
                book.getAuthorId(),
                book.getPublicationYear(),
                book.getPageNumber(),
                book.getCost()
        );
    }

}
