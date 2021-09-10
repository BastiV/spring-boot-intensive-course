package de.workshop.bookstoreapi.books;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Repository
public class BookRepository {

    @Autowired
    private ObjectMapper mapper;

    // Wird ausgeführt nachdem Spring die BookController Bean instanziiert hat
    @PostConstruct
    public List<Book> getBooks() throws IOException {
        return Arrays.asList(mapper.readValue(new File("C:/Users/DIBVOIGT/Development/Java/spring-boot-intensive-course/book-store-api/target/classes/books.json"), Book[].class));
    }

}
