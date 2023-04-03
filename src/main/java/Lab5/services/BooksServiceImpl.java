package Lab5.services;

import Lab5.DAO.BookDAO;
import Lab5.aop.annotation.MET;
import Lab5.models.Book;
import Lab5.services.serviceInterface.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BookDAO bookDAO;

    @MET
    @Override
    public List<Book> all() {
        return bookDAO.findAll();
    }

    @MET
    public List<Book> byTitle(String title) {
        return bookDAO.findByTitle(title);
    }

    @MET
    @Override
    public Book byId(int id) {
        return bookDAO.findById(id);
    }

    @MET
    @Override
    public void create(Book book) {
        enrichBook(book);
        bookDAO.create(book);
    }

    @MET
    @Override
    public void update(int id, Book book) {
        enrichBook(book);
        bookDAO.update(id, book);
    }
    @MET
    @Override
    public void delete(int id) {
        bookDAO.delete(id);
    }

    private void enrichBook(Book book) {
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        book.setUpdatedBy("Fedor");
    }

}
