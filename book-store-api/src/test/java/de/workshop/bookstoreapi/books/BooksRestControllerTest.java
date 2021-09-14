package de.workshop.bookstoreapi.books;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BooksRestControllerTest {

    @Autowired
    BooksRestController booksRestController;

    @Test
    void getAllBooks() {
        //assertEquals(3, booksRestController.getAllBooks().size());
    }
}
