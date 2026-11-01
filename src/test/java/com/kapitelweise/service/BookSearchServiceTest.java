package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.service;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withException;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.dao.BookSearchDao;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.request.BookSearchRequest;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response.BookResponse;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response.BookSearchResponse;

class BookSearchServiceTest {

    private BookSearchDao bookSearchDao;
    private BookSearchService service;
    private MockRestServiceServer server;

    @BeforeEach
    void setUp() {
        bookSearchDao = mock(BookSearchDao.class);
        service = new BookSearchService(bookSearchDao);
        RestClient.Builder builder = RestClient.builder();
        server = MockRestServiceServer.bindTo(builder).build();
        ReflectionTestUtils.setField(service, "restClient", builder.build());
    }

    @Test
    void leereSucheWirdAbgelehntOhneApiAufruf() {
        BookSearchRequest request = new BookSearchRequest(null, null, null, null);

        ResponseStatusException error = assertThrows(
                ResponseStatusException.class, () -> service.searchBooks(request));

        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
        verifyNoInteractions(bookSearchDao);
        server.verify();
    }

    @Test
    void leerzeichenWerdenAlsLeereSucheErkannt() {
        BookSearchRequest request = new BookSearchRequest("  ", "", null, "  ");

        ResponseStatusException error = assertThrows(
                ResponseStatusException.class, () -> service.searchBooks(request));

        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
        verifyNoInteractions(bookSearchDao);
        server.verify();
    }

    @Test
    void titelSucheVerarbeitetTrefferUndSpeichertBuch() {
        server.expect(requestTo(containsString("title=Hobbit")))
                .andRespond(withSuccess("""
                        {"docs":[{"title":"Der Hobbit",
                          "author_name":["J. R. R. Tolkien"],
                          "first_publish_year":1937,
                          "number_of_pages_median":310,
                          "editions":{"docs":[{"isbn":["9780261103344"]}]}}]}
                        """, MediaType.APPLICATION_JSON));
        when(bookSearchDao.saveFoundBook("Der Hobbit", "J. R. R. Tolkien",
                "9780261103344", 310, 1937)).thenReturn(42L);

        BookSearchResponse result = service.searchBooks(
                new BookSearchRequest(null, "Hobbit", null, null));

        assertNotNull(result);
        assertEquals(1, result.getDocs().size());
        BookResponse book = result.getDocs().get(0);
        assertEquals("Der Hobbit", book.getTitle());
        assertEquals("J. R. R. Tolkien", book.getAuthor());
        assertEquals("9780261103344", book.getIsbn());
        assertEquals(310, book.getPages());
        assertEquals(1937, book.getFirstPublishYear());
        assertEquals(42L, book.getFoundBookId());
        verify(bookSearchDao).saveFoundBook(
                "Der Hobbit", "J. R. R. Tolkien", "9780261103344", 310, 1937);
        server.verify();
    }

    @Test
    void autorSucheWirdAnOpenLibraryUebergeben() {
        server.expect(requestTo(containsString("author=Tolkien")))
                .andRespond(withSuccess("{\"docs\":[]}", MediaType.APPLICATION_JSON));

        BookSearchResponse result = service.searchBooks(
                new BookSearchRequest(null, null, "Tolkien", null));

        assertTrue(result.getDocs().isEmpty());
        verifyNoInteractions(bookSearchDao);
        server.verify();
    }

    @Test
    void isbnSucheWirdAnOpenLibraryUebergeben() {
        server.expect(requestTo(containsString("isbn=9780261103344")))
                .andRespond(withSuccess("{\"docs\":[]}", MediaType.APPLICATION_JSON));

        BookSearchResponse result = service.searchBooks(
                new BookSearchRequest(null, null, null, "9780261103344"));

        assertTrue(result.getDocs().isEmpty());
        verifyNoInteractions(bookSearchDao);
        server.verify();
    }

    @Test
    void erfolgloseSucheLiefertLeereListe() {
        server.expect(requestTo(containsString("title=NichtVorhanden")))
                .andRespond(withSuccess("{\"docs\":[]}", MediaType.APPLICATION_JSON));

        BookSearchResponse result = service.searchBooks(
                new BookSearchRequest(null, "NichtVorhanden", null, null));

        assertNotNull(result);
        assertTrue(result.getDocs().isEmpty());
        verifyNoInteractions(bookSearchDao);
        server.verify();
    }

    @Test
    void buchOhneIsbnWirdNichtGespeichert() {
        server.expect(requestTo(containsString("title=OhneISBN")))
                .andRespond(withSuccess("""
                        {"docs":[{"title":"Ohne ISBN", "author_name":["Unbekannt"]}]}
                        """, MediaType.APPLICATION_JSON));

        BookSearchResponse result = service.searchBooks(
                new BookSearchRequest(null, "OhneISBN", null, null));

        assertTrue(result.getDocs().isEmpty());
        verifyNoInteractions(bookSearchDao);
        server.verify();
    }

    @Test
    void nichtErreichbareApiLiefertGatewayTimeout() {
        server.expect(requestTo(containsString("title=Hobbit")))
                .andRespond(withException(new IOException("Verbindung fehlgeschlagen")));

        ResponseStatusException error = assertThrows(
                ResponseStatusException.class,
                () -> service.searchBooks(new BookSearchRequest(null, "Hobbit", null, null)));

        assertEquals(HttpStatus.GATEWAY_TIMEOUT, error.getStatusCode());
        verifyNoInteractions(bookSearchDao);
        server.verify();
    }

    @Test
    @Disabled("Die ISBN-Formatvalidierung fehlt noch in BookSearchService")
    void ungueltigeIsbnWirdOhneApiAufrufAbgelehnt() {
        ResponseStatusException error = assertThrows(
                ResponseStatusException.class,
                () -> service.searchBooks(new BookSearchRequest(null, null, null, "123ABC")));

        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
        verifyNoInteractions(bookSearchDao);
        server.verify();
    }

@Test
void buchOhneTitelWirdNichtGespeichert() {
    server.expect(requestTo(containsString("title=Unbekannt")))
            .andRespond(withSuccess("""
                    {"docs":[{"editions":{"docs":[
                    {"isbn":["9780261103344"]}
                    ]}}]}
                    """, MediaType.APPLICATION_JSON));

    BookSearchResponse result = service.searchBooks(
            new BookSearchRequest(null, "Unbekannt", null, null));

    assertTrue(result.getDocs().isEmpty());
    verifyNoInteractions(bookSearchDao);
    server.verify();
}

}
