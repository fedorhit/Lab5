package Lab5.services.serviceInterface;

import Lab5.models.Person;

import java.util.List;

public interface PeopleService {
    List<Person> all();
    List<Person> byName(String name);
    Person byId(int id);
    void create(Person person);
    void update(int id, Person person);
    void delete(int id);
}
