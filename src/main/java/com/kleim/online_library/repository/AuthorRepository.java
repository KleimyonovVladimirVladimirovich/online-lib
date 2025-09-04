package com.kleim.online_library.repository;

import com.kleim.online_library.entity.AuthorEntity;
import com.kleim.online_library.models.author.AuthorDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    boolean existsByName(String name);

    @Transactional
    @Modifying
    @Query("""
           UPDATE BookEntity b
           SET b.authorId = NULL
           WHERE b.authorId = :authorId
        """)
    void deleteAuthorFromBooks(Long authorId);

//    @Query("""
//        SELECT a from AuthorEntity a
//        JOIN FETCH a.bookList
//        """)
//    List<AuthorEntity> findAllWithBooks();

//    @Query("SELECT a from AuthorEntity a")
//    @EntityGraph(attributePaths = "books")
//    List<AuthorEntity> findAllWithBooks();

    @Query("SELECT a from AuthorEntity a")
    @EntityGraph(value = "author-with-books")
    List<AuthorEntity> findAllWithBooks();
}
