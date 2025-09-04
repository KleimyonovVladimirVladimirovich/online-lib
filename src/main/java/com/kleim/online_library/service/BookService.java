package com.kleim.online_library.service;

import com.kleim.online_library.entity.BookEntity;
import com.kleim.online_library.models.book.Book;
import com.kleim.online_library.models.book.BookConverter;
import com.kleim.online_library.models.book.BookEntityConverter;
import com.kleim.online_library.models.book.BookSearchFilter;
import com.kleim.online_library.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import java.util.*;

@Service
public class BookService {


    private final BookRepository bookRepository;
    private final BookEntityConverter bookEntityConverter;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, BookEntityConverter bookEntityConverter, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.bookEntityConverter = bookEntityConverter;
        this.authorService = authorService;
    }


    public Book createBook(Book bookToCrete) {
        checkAuthorExistence(bookToCrete.authorId());

        var bookToSave = bookEntityConverter.toBookEntity(bookToCrete);
        var savedEntity = bookRepository.save(bookToSave);

        return bookEntityConverter.toBookFromEntity(savedEntity);
    }

    public Book findById(Long id) {
        var foundBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No found book by id=%s".formatted(id)
                ));

        return bookEntityConverter.toBookFromEntity(foundBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("No found book by id=%s".formatted(id));
        }
        bookRepository.deleteById(id);
    }

    public Book updateBook(
            Long id,
            Book bookToUpdate
    ) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("No found book by id=%s".formatted(id));
        }
        checkAuthorExistence(bookToUpdate.id());

        bookRepository.updateBook(
                id,
                bookToUpdate.name(),
                bookToUpdate.authorId(),
                bookToUpdate.publicationYear(),
                bookToUpdate.pageNumber(),
                bookToUpdate.cost()
        );

        return bookEntityConverter.toBookFromEntity(
                bookRepository.findById(id).orElseThrow()
        );
    }

    private void checkAuthorExistence(Long authorId) {
        if (!authorService.isAuthorExistsById(authorId)) {
            throw new IllegalArgumentException("Author not exists by id=%s"
                    .formatted(authorId));
        }
    }

    public List<Book> searchAllBooks(BookSearchFilter bookSearchFilter) {
        int pageSize = bookSearchFilter.pageSize() != null ? bookSearchFilter.pageSize() : 3;
        int pageNumber = bookSearchFilter.pageNumber() != null ? bookSearchFilter.pageNumber() : 0;
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        return bookRepository.findAllBooks(bookSearchFilter.authorId(), bookSearchFilter.cost(), pageable)
                .stream()
                .map(bookEntityConverter::toBookFromEntity)
                .toList();

    }
}

