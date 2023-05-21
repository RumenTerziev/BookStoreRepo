package bg.rumen.Bookstore.controller;

import bg.rumen.Bookstore.interfaces.BookRepository;
import bg.rumen.Bookstore.interfaces.CommentRepository;
import bg.rumen.Bookstore.models.Book;
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
    public List<Book> getAllBooks() {
        return this.bookRepository.getBooks();
    }

    @PostMapping
    public List<Book> addBook(@RequestBody Book book) {
        this.bookRepository.addBook(book);
        return this.bookRepository.getBooks();
    }


    @PatchMapping("/{id}")
    public List<Book> updateBook(@RequestBody Book book, @PathVariable String id) {
        this.bookRepository.editBook(book, id);
        return this.bookRepository.getBooks();
    }


    @DeleteMapping("/{id}")
    public List<Book> deleteBook(@PathVariable String id) {
        this.bookRepository.deleteBookById(id);
        this.commentsRepository.removeAllComments(id);
        return this.bookRepository.getBooks();
    }


    @GetMapping("/comments/{id}")
    public List<Comment> getComments(@PathVariable String id) {
        return this.commentsRepository.getCommentsById(id);
    }


    @PostMapping("/comments/{id}")
    public List<Comment> addComment(@PathVariable String id, @RequestBody Comment comment) {
        comment.setBookId(id);
        this.commentsRepository.addComment(comment);
        return this.commentsRepository.getCommentsById(id);
    }


    @GetMapping("/{title}")
    public List<Book> getAllBooksWithTitle(@PathVariable String title) {
        return this.bookRepository.getAllBooksWithTitle(title);
    }


    @DeleteMapping("/comments/{bookId}/{id}")
    public void deleteCommentById(@PathVariable String bookId, @PathVariable String id) {
       this.commentsRepository.deleteCommentById(bookId, id);
    }
}
