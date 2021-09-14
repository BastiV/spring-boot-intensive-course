package de.workshop.bookstoreapi.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*// Wird ausgeführt nachdem Spring die BookController Bean instanziiert hat
    @PostConstruct
    public List<Book> getBooks() throws IOException {
        //Read from File
        //return Arrays.asList(mapper.readValue(new File("C:/Users/DIBVOIGT/Development/Java/spring-boot-intensive-course/book-store-api/target/classes/books.json"), Book[].class));
    }*/

    public List<Book> findAllBooks() {
        String sql = "SELECT * FROM book";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
    }

    public void createBook(Book book) {
        String sql = "INSERT INTO book (id, title, description, author, isbn) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getAuthor(),
                book.getIsbn()
        );
    }

    public void deleteBook(Book book) {
        String sql = "DELETE FROM book WHERE isbn = ?";

        jdbcTemplate.update(
                sql,
                book.getIsbn()
        );
    }

}
