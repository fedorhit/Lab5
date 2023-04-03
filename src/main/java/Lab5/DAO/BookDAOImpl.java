package Lab5.DAO;

import Lab5.models.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookDAOImpl implements BookDAO {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Book> findAll() {
        return entityManager.createQuery("from Book", Book.class)
                .getResultList();
    }

    public List<Book> findByTitle(String title) {
       return entityManager.createQuery("FROM Book b WHERE b.title LIKE :pTitle", Book.class)
             .setParameter("pTitle", title+"%")
        ///return entityManager.createQuery("from Book p", Book.class)
                .getResultList();
    }

    @Override
    public Book findById(int id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    @Transactional
    public void create(Book book) {
        entityManager.persist(book);
    }

    @Override
    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookUpdate = entityManager.find(Book.class, id);

        bookUpdate.setTitle(updatedBook.getTitle());
        bookUpdate.setEdition(updatedBook.getEdition());

        entityManager.merge(bookUpdate);
    }

    @Override
    @Transactional
    public void delete(int id) {
        entityManager.remove(entityManager.find(Book.class, id));
    }
}
