package com.kleim.online_library.error;

import java.time.LocalDateTime;

public record ServerErrorDTO(

        String message,

        String detailMessage,

        LocalDateTime dateTime

) { }