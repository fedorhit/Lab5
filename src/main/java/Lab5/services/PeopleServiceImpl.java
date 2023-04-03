package Lab5.services;

import Lab5.DAO.PersonDAO;
import Lab5.aop.annotation.MET;
import Lab5.models.Person;
import Lab5.services.serviceInterface.PeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PeopleServiceImpl implements PeopleService {

    private final PersonDAO personDAO;

    @MET
    @Override
    public List<Person> all() {
        return personDAO.findAll();
    }

    @MET
    @Override
    public List<Person> byName(String name) {
        return personDAO.findByName(name);
    }

    @MET
    @Override
    public Person byId(int id) {
        return personDAO.findById(id);
    }

    @MET
    @Override
    public void create(Person person) {
        enrichPerson(person);
        personDAO.create(person);
    }

    @MET
    @Override
    public void update(int id, Person person) {
        enrichPerson(person);
        personDAO.update(id, person);
    }
    @MET
    @Override
    public void delete(int id) {
        personDAO.delete(id);
    }

    private void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setUpdatedBy("Fedor");
    }

}
