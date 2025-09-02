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
    private final BookConverter bookConverter;

    public BookService(BookRepository bookRepository, BookEntityConverter bookEntityConverter, BookConverter bookConverter) {
        this.bookRepository = bookRepository;
        this.bookEntityConverter = bookEntityConverter;
        this.bookConverter = bookConverter;
    }

    public List<Book> getAllBooks() {

        return null;
    }

    public Book createBook(Book bookToCreate) {
        var bookToSave = new BookEntity(
                null,
                bookToCreate.name(),
                bookToCreate.authorId(),
                bookToCreate.publicationYear(),
                bookToCreate.pageNumber(),
                bookToCreate.cost()
        );

        var savedEntity = bookRepository.save(bookToSave);
        return new Book(
                savedEntity.getId(),
                savedEntity.getName(),
                savedEntity.getAuthorName(),
                savedEntity.getPublicationYear(),
                savedEntity.getPageNumber(),
                savedEntity.getCost()
        );

    }


    public Book findById(Long id) {
        var foundBook = bookRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No such user with id: %s".formatted(id)));
        return bookEntityConverter.toBookFromEntity(foundBook);

    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("No found book with id: %s".formatted(id));
        }
        bookRepository.deleteById(id);
    }

    public Book updateBook(
            Long id,
            Book updateToBook
    ) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("No such book with id: %s".formatted(id));
        }
        bookRepository.updateBook(
                id,
                updateToBook.name(),
                updateToBook.authorId(),
                updateToBook.publicationYear(),
                updateToBook.pageNumber(),
                updateToBook.cost()
                );
//        var bookUpdate = new BookEntity(
//                id,
//                updateToBook.name(),
//                updateToBook.authorName(),
//                updateToBook.publicationYear(),
//                updateToBook.pageNumber(),
//                updateToBook.cost()
//        );
//        var updatedBook = bookRepository.save(bookUpdate);
        return bookEntityConverter.toBookFromEntity(
                bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No element with id %s".formatted(id))));

    }

    public List<Book> searchAllBooks(BookSearchFilter bookSearchFilter) {
        int pageSize = bookSearchFilter.pageSize() != null ? bookSearchFilter.pageSize() : 3;
        int pageNumber = bookSearchFilter.pageNumber() != null ? bookSearchFilter.pageNumber() : 0;
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        return bookRepository.findAllBooks(bookSearchFilter.authorId(), bookSearchFilter.cost(), pageable)
                .stream()
                .map(bookEntityConverter::toBookFromEntity)
                .toList();

//        var findAllBooks = bookRepository.findAll()
//                .stream()
//                .filter(it -> authorName == null || it.getAuthorName().equals(authorName))
//                .filter(it -> cost == null || it.getCost() < 5000)
//                .toList();
//        return bookEntityConverter.toBookFromEntity(findAllBooks);

    }
}

