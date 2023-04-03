package Lab5.ui;

import Lab5.AppException;
import Lab5.DTO.BookDTO;
import Lab5.DTO.PersonDTO;
import Lab5.models.Book;
import Lab5.models.Person;
import Lab5.services.serviceInterface.CurrentLocaleService;
import Lab5.services.serviceInterface.PeopleService;
import Lab5.exception.PersonNotFoundException;
import Lab5.services.serviceInterface.BooksService;
import Lab5.exception.BookNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {
    private final PeopleService peopleService;
    private final BooksService booksService;
    private final IO io;
    private final CurrentLocaleService currentLocaleService;
    private final ModelMapper modelMapper;

    public enum State {
        MAIN_MENU("main menu");
        @Getter
        private final String title;

        State(String title) {
            this.title = title;
        }
    }

    @Getter
    private final State state = State.MAIN_MENU;

    @ShellMethod(value = "change current language", key = {"language", "lang", "l"})
    @ShellMethodAvailability("availableInMainMenu")
    public void setLanguage(String lang) {
        try {
            currentLocaleService.set(lang.strip().toLowerCase());
            io.interPrintln("setLanguageSuccess");
        }
        catch (AppException e) {
            io.interPrintln("setLanguageFailed");
            io.interPrintln(e.getMessage(), e.getParams());
        }
    }

    @ShellMethod(key = {"people", "p"})
    public List<PersonDTO> getAll(){
        var people = peopleService.all().stream()
                .map(this::convertToPersonDTO)
                .collect(Collectors.toList());

        if (people.isEmpty()) {
            io.interPrintln("nobodyFound");
            throw new PersonNotFoundException("The list is empty");
        } else {
            io.interPrintln("people");
            return people;
        }
    }

    @ShellMethod(key = {"books","b"})
    public List<BookDTO> allBooks(){
        var books = booksService.all().stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());

        if (books.isEmpty()) {
            io.interPrintln("noBooksFound");
            throw new BookNotFoundException("The list is empty");
        } else {
            io.interPrintln("books");
            return books;
        }
    }

    @ShellMethod(key = {"bookByTitle","bt"})
    public List<BookDTO> getBooksByTitle(String title){
        var books = booksService.byTitle(title).stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());

        if (books.isEmpty()) {
            io.interPrintln("bookByTitle_failed");
            throw new BookNotFoundException("The list is empty");
        } else {
            io.interPrintln("booksByTitle_ok");
            return books;
        }
    }

    @ShellMethod(key = {"personByName","pn"})
    public List<PersonDTO> getPeopleByName(String name){
        var people = peopleService.byName(name).stream()
                .map(this::convertToPersonDTO)
                .collect(Collectors.toList());

        if (people.isEmpty()) {
            io.interPrintln("personByName_failed");
            throw new PersonNotFoundException("The list is empty");
        } else {
            io.interPrintln("personByName_ok");
            return people;
        }
    }

    @ShellMethod(key = {"personById","pid"})
    public PersonDTO getById(int id) {
        var person = convertToPersonDTO(peopleService.byId(id));
        if (person == null) {
            io.interPrintln("personById_failed");
            throw new PersonNotFoundException("The person was not found");
        } else {
            io.interPrintln("personById_ok");
            return person;
        }
    }
    @ShellMethod(key = {"bookById","bid"})
    public BookDTO bookById(int id) {
        var book = convertToBookDTO(booksService.byId(id));
        if (book == null) {
            io.interPrintln("bookById_failed");
            throw new BookNotFoundException("The book is not found");
        } else {
            io.interPrintln("bookById_Ok");
            return book;
        }
    }

    @ShellMethod(key = "createPerson")
    public void createPerson(String name, int age) {
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        peopleService.create(person);
        io.interPrintln("createPerson_ok");
    }

    @ShellMethod(key = "updatePerson")
    public void updatePerson(int id, String name, int age) {
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setAge(age);
        peopleService.update(id, person);
        io.interPrintln("update-successful");
    }

    @ShellMethod(key = "deletePerson")
    public void deletePerson(int id) {
        peopleService.delete(id);
        io.interPrintln("delete-successful");
    }

    @ShellMethod(key = "createBook")
    public void createBook(String title, int edition) {
        Book book = new Book();
        book.setTitle(title);
        book.setEdition(edition);
        booksService.create(book);
        io.interPrintln("create-successful");
    }

    @ShellMethod(key = "updateBook")
    public void updateBook(int id, String title, int edition) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setEdition(edition);
        booksService.update(id, book);
        io.interPrintln("update-successful");
    }
    @ShellMethod(key = "deleteBook")
    public void deleteBook(int id) {
        booksService.delete(id);
        io.interPrintln("delete-successful");
    }


    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }
    private BookDTO convertToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }

}