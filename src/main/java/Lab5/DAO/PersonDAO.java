package Lab5.DAO;

import Lab5.models.Person;

import java.util.List;

public interface PersonDAO {
    List<Person> findAll();
    Person findById(int id);
    List<Person> findByName(String name);
    void create(Person person);
    void update(int id, Person updatedPerson);
    void delete(int id);
}
