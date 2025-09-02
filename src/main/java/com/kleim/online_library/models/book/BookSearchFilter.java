package com.kleim.online_library.models.book;

import jakarta.validation.constraints.Min;

public record BookSearchFilter(

        Long authorId,
        Integer cost,
        @Min(0)
        Integer pageNumber,
        @Min(3)
        Integer pageSize
) {
}
