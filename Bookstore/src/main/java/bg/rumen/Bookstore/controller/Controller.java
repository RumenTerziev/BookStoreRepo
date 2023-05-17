package bg.rumen.Bookstore.controller;


import bg.rumen.Bookstore.models.Book;
import bg.rumen.Bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookstore")
public class Controller {

    @Autowired
    @Qualifier("getRepo")
    private BookRepository bookRepository;


    @GetMapping
    public List<Book> getAllBooks() {
        return this.bookRepository.getBooks();
    }

    @PostMapping
    public List<Book> addBook(@RequestBody Book book) {
        this.bookRepository.addBook(book);
        return this.bookRepository.getBooks();
    }


    @PatchMapping("/{name}")
    public List<Book> updateBook(@RequestBody Book book, @PathVariable String name) {
        this.bookRepository.deleteBookByTitle(name);
        this.bookRepository.addBook(book);
        return this.bookRepository.getBooks();
    }


    @DeleteMapping("/{name}")
    public List<Book> deleteBook(@PathVariable String name) {
        this.bookRepository.deleteBookByTitle(name);
        return this.bookRepository.getBooks();
    }

}
