package com.kleim.online_library.models.book;

public record Book(

        Long id,

        String name,

        Long authorId,

        Integer publicationYear,

        Integer pageNumber,

        Integer cost

) {
}