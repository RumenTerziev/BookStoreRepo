package bg.rumen.bookstore.controller;

import bg.rumen.bookstore.interfaces.BookRepository;
import bg.rumen.bookstore.interfaces.CommentRepository;
import bg.rumen.bookstore.models.Book;
import bg.rumen.bookstore.models.PageResult;
import bg.rumen.bookstore.params.BookSearchParams;
import bg.rumen.bookstore.models.Comment;
import bg.rumen.bookstore.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookstore")
public class Controller {

    @Autowired
    private BookRepository bookRepository;


    @Autowired
    private CommentRepository commentsRepository;


    @GetMapping
    public PageResult<Book> getBooks(BookSearchParams searchParams, PageParams pageParams) {
        return this.bookRepository.getBooks(searchParams, pageParams);
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
    public PageResult<Comment> getComments(@PathVariable Integer id, PageParams pageParams) {
        return this.commentsRepository.getComments(id, pageParams);
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
