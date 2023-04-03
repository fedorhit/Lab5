package Lab5.DAO;

import Lab5.models.Book;

import java.util.List;

public interface BookDAO {
    List<Book> findAll();
    Book findById(int id);
    List<Book> findByTitle(String title);
    void create(Book book);
    void update(int id, Book updatedBook);
    void delete(int id);
}
