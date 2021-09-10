package de.workshop.bookstoreapi.books;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
public class WebMvcTest {

    @MockBean
    BookService bookService;

    @Autowired
    BooksRestController booksRestController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void setUp() throws Exception {
        Mockito.when(bookService.getBooks()).thenReturn(new ArrayList<>());
        mockMvc.perform(get(BooksRestController.REQUEST_URL))
                .andDo(print())
                .andExpect(status().isOk());

    }


}
