package de.workshop.bookstoreapi.books;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerIntegrationTest {

    @TestConfiguration
    static class Config {
        @Bean
        public ObjectMapper mapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            return mapper;
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BooksRestController booksRestController;

    @Test
    void getAllBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BooksRestController.REQUEST_URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", is("Clean Code")));
    }

    @Test
    void getAllBooksJsonContent() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BooksRestController.REQUEST_URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", is("Clean Code")))
                .andReturn();
        String jsonPayload = mvcResult.getResponse().getContentAsString();

        Book[] books = objectMapper.readValue(jsonPayload, Book[].class);

        objectMapper.writeValue( System.out, books);
        assertEquals(3, books.length);
        assertEquals("Clean Code", books[1].getTitle());
    }

    @Test
    void testWithRestAssuredMockMVvc() {
        RestAssuredMockMvc.standaloneSetup(booksRestController);
        RestAssuredMockMvc.given().
                log().all()
                .when()
                .get("/books")
                .then()
                .log().all()
                .statusCode(200)
                .body("author[0]", equalTo("Erich Gamma"));
    }

    @Test
    void testWithGivenIsbn() {
        RestAssuredMockMvc.standaloneSetup(booksRestController);
        RestAssuredMockMvc.given().
                log().all()
                .when()
                .get("/books/978-0201633610")
                .then()
                .log().all()
                .statusCode(200)
                .body("author", equalTo("Erich Gamma"));
    }
}
