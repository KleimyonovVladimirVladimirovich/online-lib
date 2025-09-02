package com.kleim.online_library.repository;

import com.kleim.online_library.entity.BookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {


    @Query(value = """
            SELECT b FROM BookEntity b 
            WHERE (:authorName IS NULL OR b.authorId = :authorId)
             AND (:cost IS NULL OR b.cost < :cost)""")
    List<BookEntity> findAllBooks(
            Long authorId,
            Integer cost,
            Pageable pageable
    );

    @Transactional
    @Modifying
    @Query(value = """
           UPDATE BookEntity b
            SET b.name = :name, b.authorId = :authorId, b.publicationYear = :publicationYear, b.pageNumber = :pageNumber, b.cost = :cost
            WHERE b.id = :id
          """)
    void updateBook(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("authorId") Long authorId,
            @Param("publicationYear") Integer publicationYear,
            @Param("pageNumber") Integer pageNumber,
            @Param("cost") Integer cost
    );

}

