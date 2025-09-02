package com.kleim.online_library.models.book;

import org.springframework.stereotype.Component;

@Component
public class BookConverter {

    public BookDTO toBookDTO(Book book) {
        return new BookDTO(
                book.id(),
                book.name(),
                book.authorId(),
                book.publicationYear(),
                book.pageNumber(),
                book.cost()
        );
    }

    public Book toBook(BookDTO bookDTO) {
        return new Book(
                bookDTO.id(),
                bookDTO.name(),
                bookDTO.authorId(),
                bookDTO.publicationYear(),
                bookDTO.pageNumber(),
                bookDTO.cost()
        );
    }
}
