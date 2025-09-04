package com.kleim.online_library.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Entity
@NamedEntityGraph(
        name = "author-with-books",
        attributeNodes = {
                @NamedAttributeNode("bookList")
        }
)
@Table(name = "author")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "birth_year", nullable = false)
    private Integer birthYear;

    @OneToMany
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Set<BookEntity> bookList;

    public AuthorEntity() {}

    public AuthorEntity(Long id, String name, Integer birthYear, Set<BookEntity> bookList) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.bookList = bookList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Set<BookEntity> getBookList() {
        return bookList;
    }

    public void setBookList(Set<BookEntity> bookList) {
        this.bookList = bookList;
    }
}
