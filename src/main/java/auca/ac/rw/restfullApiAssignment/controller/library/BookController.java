package auca.ac.rw.restfullApiAssignment.controller.library;

import java.util.*;
import java.util.stream.Collectors;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import auca.ac.rw.restfullApiAssignment.model.library.Book;

@RestController
@RequestMapping
public class BookController {

    private List<Book> books = new ArrayList<>();

    public BookController() {
        books.add(new Book(1L, "The Rosary", "Francoise", "2345GHEI33", 1925));
        books.add(new Book(2L, "Half of a yellow sun", "Chimamanda Ngozi Adichie", "2345GHEI33", 1960));
        books.add(new Book(3L, "No longer at ease", "Chinue Achebe", "2345GHEI33", 1949));
    }

    @GetMapping("/api/books")
    public List<Book> getAllBooks() {
        return books;
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/books/search")
    public List<Book> searchByTitle(@RequestParam String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());          
    }

    @PostMapping("/api/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        books.add(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }    
    }

}
