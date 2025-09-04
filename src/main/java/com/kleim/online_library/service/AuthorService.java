package com.kleim.online_library.service;

import com.kleim.online_library.models.author.Author;
import com.kleim.online_library.models.author.AuthorEntityConverter;
import com.kleim.online_library.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorEntityConverter entityConverter;

    public AuthorService(
            AuthorRepository authorRepository,
            AuthorEntityConverter entityConverter
    ) {
        this.authorRepository = authorRepository;
        this.entityConverter = entityConverter;
    }

    public Author createAuthor(Author author) {
        if (authorRepository.existsByName(author.name())) {
            throw new IllegalArgumentException("Author name already taken");
        }
        var createEntityAuthor = entityConverter.toAuthorEntity(author);
        var createdAuthor = authorRepository.save(createEntityAuthor);

        return entityConverter.toAuthorFromEntity(createdAuthor);

    }

    public boolean isAuthorExistsById(Long id) {
        return authorRepository.existsById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAllWithBooks()
                .stream()
                .map(entityConverter::toAuthorFromEntity)
                .toList();
    }

    @Transactional
    public void deleteAuthor(Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new EntityNotFoundException("Not found author by id=%s"
                    .formatted(authorId));
        }
        authorRepository.deleteAuthorFromBooks(authorId);
        authorRepository.deleteById(authorId);
    }
}
