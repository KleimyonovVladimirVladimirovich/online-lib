package com.kleim.online_library.repository;

import com.kleim.online_library.models.author.AuthorDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorDTO, Long> {

    void getAllAuthors();
}
