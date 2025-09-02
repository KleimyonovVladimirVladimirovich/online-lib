package com.kleim.online_library.models.book;

import jakarta.validation.constraints.*;

public record BookDTO(

        @Null
        Long id,

        @NotBlank
        @Size(min = 5, max = 30)
        String name,

        @NotNull
        Long authorId,

        @NotNull
        Integer publicationYear,

        @NotNull
        @Min(0)
        Integer pageNumber,

        @NotNull
        @Min(250)
        Integer cost

) { }