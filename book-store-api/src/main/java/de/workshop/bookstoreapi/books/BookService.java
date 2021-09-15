package de.workshop.bookstoreapi.books;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    private List<Book> books;

    // Constructor injection of service
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void init() {
        books = Collections.emptyList();

        books = bookRepository.findAllBooks();
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book getSingleBook(String isbn) throws BookException {
        return this.books.stream().filter(book -> hasIsbn(book, isbn)).findFirst().orElseThrow(BookException::new);
    }

    public Book searchBookByAuthor(String author) throws BookException {
        return this.books.stream().filter(book -> hasAuthor(book, author)).findFirst().orElseThrow(BookException::new);
    }

    public List<Book> searchBooks(BookSearchRequest request) {
        return this.books.stream()
                .filter(book -> hasIsbn(book, request.getIsbn()))
                .filter(book -> hasAuthor(book, request.getAuthor()))
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                Collections::unmodifiableList
                        )
                );
    }

    // Should be implemented in Repository oder Service Class
    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    // Should be implemented in Repository oder Service Class
    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }

}
