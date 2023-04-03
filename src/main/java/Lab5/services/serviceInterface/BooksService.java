package Lab5.services.serviceInterface;

import Lab5.models.Book;

import java.util.List;

public interface BooksService {
    List<Book> all();
    List<Book> byTitle(String title);
    Book byId(int id);
    void create(Book book);
    void update(int id, Book book);
    void delete(int id);
}
