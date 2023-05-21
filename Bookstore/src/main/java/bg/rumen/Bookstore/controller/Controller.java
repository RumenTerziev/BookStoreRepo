package bg.rumen.Bookstore.controller;

import bg.rumen.Bookstore.interfaces.BookRepository;
import bg.rumen.Bookstore.interfaces.CommentRepository;
import bg.rumen.Bookstore.models.Book;
import bg.rumen.Bookstore.models.BookSearchParams;
import bg.rumen.Bookstore.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import static bg.rumen.Bookstore.qualifiers.BeanQualifiers.*;

import java.util.List;


@RestController
@RequestMapping("/api/bookstore")
public class Controller {

    @Autowired
    private BookRepository bookRepository;


    @Autowired
    @Qualifier(COMMENTS_REPO)
    private CommentRepository commentsRepository;


    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String title) {
        return this.bookRepository.getBooks(title);
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {
        this.bookRepository.addBook(book);
    }


    @PatchMapping("/{id}")
    public void updateBook(@RequestBody Book book) {
        this.bookRepository.editBook(book);
    }


    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id) {
        this.bookRepository.deleteBookById(id);
        this.commentsRepository.removeAllComments(id);
    }


    @GetMapping("/comments/{id}")
    public List<Comment> getComments(@PathVariable Integer id) {
        return this.commentsRepository.getCommentById(id);
    }


    @PostMapping("/comments/{id}")
    public void addComment(@PathVariable Integer id, @RequestBody Comment comment) {
        comment.setBookId(id);
        this.commentsRepository.addComment(comment);
    }


    @DeleteMapping("/comments/{id}")
    public void deleteCommentById(@PathVariable Integer id) {
       this.commentsRepository.deleteCommentById(id);
    }
}
