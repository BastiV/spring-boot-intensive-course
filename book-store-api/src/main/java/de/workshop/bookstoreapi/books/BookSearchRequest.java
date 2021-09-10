package de.workshop.bookstoreapi.books;

import lombok.Data;

@Data
public class BookSearchRequest {
    private String author;
    private String isbn;
}
