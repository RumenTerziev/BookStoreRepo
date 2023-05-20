package bg.rumen.Bookstore.controller;


import bg.rumen.Bookstore.models.Book;
import bg.rumen.Bookstore.models.Comment;
import bg.rumen.Bookstore.repository.BookRepository;
import bg.rumen.Bookstore.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import static bg.rumen.Bookstore.qualifiers.BeanQualifiers.*;

import java.util.List;


@RestController
@RequestMapping("/api/bookstore")
public class Controller {
    private Integer newID = 3;

    @Autowired
    private BookRepository bookRepository;


    @Autowired
    @Qualifier(COMMENTS_REPO)
    private CommentsRepository commentsRepository;


    @GetMapping
    public List<Book> getAllBooks() {
        return this.bookRepository.getBooks();
    }

    @PostMapping
    public List<Book> addBook(@RequestBody Book book) {
        book.setId(newID);
        newID++;
        this.bookRepository.addBook(book);
        return this.bookRepository.getBooks();
    }


    @PatchMapping("/{id}")
    public List<Book> updateBook(@RequestBody Book book, @PathVariable Integer id) {
        this.bookRepository.deleteBookById(id);
        this.bookRepository.addBook(book);
        return this.bookRepository.getBooks();
    }


    @DeleteMapping("/{id}")
    public List<Book> deleteBook(@PathVariable Integer id) {
        this.bookRepository.deleteBookById(id);
        return this.bookRepository.getBooks();
    }


    @GetMapping("/{id}")
    public List<String> getComments(@PathVariable Integer id) {
        return this.commentsRepository.getCommentsById(id);
    }


    @PostMapping("/{id}")
    public List<String> addComment(@PathVariable Integer id, @RequestBody Comment comment) {
        comment.setBookId(id);
        this.commentsRepository.addComment(comment);
        return this.commentsRepository.getCommentsById(id);
    }


//    @GetMapping("/{title}")
//    public Book getBookByTitle(@PathVariable String title) {
//        return this.bookRepository.getBookByTitle(title);
//    }

}
