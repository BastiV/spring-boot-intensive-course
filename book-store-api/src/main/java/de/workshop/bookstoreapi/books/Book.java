package de.workshop.bookstoreapi.books;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {
    private String title;
    private String description;
    private String author;
    private String isbn;
}
