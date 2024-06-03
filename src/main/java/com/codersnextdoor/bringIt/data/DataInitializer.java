package com.codersnextdoor.bringIt.data;

import com.codersnextdoor.bringIt.api.address.Address;
import com.codersnextdoor.bringIt.api.address.AddressRepository;
import com.codersnextdoor.bringIt.api.todo.Todo;
import com.codersnextdoor.bringIt.api.todo.TodoRepository;
import com.codersnextdoor.bringIt.api.user.User;
import com.codersnextdoor.bringIt.api.user.UserRepository;
import jakarta.annotation.PostConstruct;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TodoRepository todoRepository;

    @PostConstruct
    @Transactional
    public void init() {
        // Check if users already exist
        List<User> users = this.userRepository.findAll();
        if (!users.isEmpty()) {
            return; // Exit if users exist
        }

        // Check if addresses already exist
        List<Address> addresses = this.addressRepository.findAll();
        if (!addresses.isEmpty()) {
            return; // Exit if addresses exist
        }

        // Check if todos already exist
        List<Todo> todos = this.todoRepository.findAll();
        if (!todos.isEmpty()) {
            return; // Exit if todos exist
        }


        // Create and save address
        Address address1 = new Address();
        address1.setCity("Wien");
        address1.setStreetNumber("Döblingerstr. 224");
        address1.setPostalCode("1190");

        addressRepository.save(address1);

        Address address2 = new Address();
        address2.setCity("Wien");
        address2.setStreetNumber("Mariahilferstr. 12");
        address2.setPostalCode("1070");

        addressRepository.save(address2);

        Address address3 = new Address();
        address3.setCity("Pressbaum");
        address3.setStreetNumber("Jurekstraße 12");
        address3.setPostalCode("3021");

        addressRepository.save(address3);

        Address address4 = new Address();
        address4.setCity("Pressbaum");
        address4.setStreetNumber("Hauptstraße 80");
        address4.setPostalCode("3021");

        addressRepository.save(address4);


        // Create and save user
        User user1 = new User();
        user1.setFirstName("Sarah");
        user1.setLastName("Schmidt");
        user1.setDateOfBirth(LocalDate.of(1990, 5,10));
        user1.setUsername("Sarah1111");
        user1.setPasswordHash("xxx");
        user1.setEmail("sarah@gmail.com");
        user1.setPhone("+43676444818027");
        user1.setAddress(address1);

        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Markus");
        user2.setLastName("Müller");
        user2.setDateOfBirth(LocalDate.of(1980, 5,13));
        user2.setUsername("Markus12345");
        user2.setPasswordHash("yyy");
        user2.setEmail("markus@gmail.com");
        user2.setPhone("+436508280857");
        user2.setAddress(address1);

        userRepository.save(user2);

        User user3 = new User();
        user3.setFirstName("Susanne");
        user3.setLastName("Strudel");
        user3.setDateOfBirth(LocalDate.of(1974, 9,24));
        user3.setUsername("Susi04");
        user3.setPasswordHash("ppp");
        user3.setEmail("susi@gmail.com");
        user3.setPhone("+436643475661");
        user3.setAddress(address2);

        userRepository.save(user3);

        // Create and save todo
        Todo todo1 = new Todo();
        todo1.setCreatedAt(LocalDateTime.now());
        todo1.setTitle("Supermarktabholung");
        todo1.setDescription("Bitte um folgende Mitnahme: Roggenbrot, BioMilch 2l");
        todo1.setAddInfo("Bitte Frischmilch oder länger frisch, keine H-Milch");
        todo1.setStatus("In Arbeit");
        todo1.setLocation("Billa, Hofer, Spar");
        todo1.setUserOffered(user1);
        todo1.setExpiresAt(LocalDateTime.of(2024,6,5, 10,0));
        todo1.setUserTaken(user3);

        todoRepository.save(todo1);

        Todo todo2 = new Todo();
        todo2.setCreatedAt(LocalDateTime.now());
        todo2.setTitle("Apothekenabholung");
        todo2.setDescription("Bitte um folgende Abholung: Mexalen 500mg");
        todo2.setAddInfo("Bereits bezahlt, wurde bestellt ist abholbereit");
        todo2.setStatus("Offen");
        todo2.setLocation("Apotheke");
        todo2.setUserOffered(user3);
        todo2.setExpiresAt(LocalDateTime.of(2024,6,5, 10,0));

        todoRepository.save(todo2);

        Todo todo3 = new Todo();
        todo3.setCreatedAt(LocalDateTime.now());
        todo3.setTitle("Drogerie");
        todo3.setDescription("Bitte um folgende Besorgung: Milchpulver PRE1 Babynahrung");
        todo3.setAddInfo("Bitte von Hipp, Alternative DM Eigenmarke");
        todo3.setStatus("Offen");
        todo3.setLocation("DM, Bipa, Müller");
        todo3.setUserOffered(user2);
        todo3.setExpiresAt(LocalDateTime.of(2024,6,5, 10,0));

        todoRepository.save(todo3);

        Todo todo4 = new Todo();
        todo4.setCreatedAt(LocalDateTime.now());
        todo4.setTitle("Supermarkt");
        todo4.setDescription("Bitte um folgende Besorgung: 1kg Bio Äpfel, 2 Gurken, Ziegenkäse");
        todo4.setAddInfo("Bitte Bio, wenn nicht schön /vorhanden dann normal");
        todo4.setStatus("Offen");
        todo4.setLocation("Billa, Hofer, Lidl,Spar");
        todo4.setUserOffered(user2);
        todo4.setExpiresAt(LocalDateTime.of(2024,6,5, 10,0));

        todoRepository.save(todo4);

        Todo todo5= new Todo();
        todo5.setCreatedAt(LocalDateTime.now());
        todo5.setTitle("Baumarkt");
        todo5.setDescription("Bitte um folgende Besorgung: 1 Kartusche Silikon grau");
        todo5.setAddInfo("Bitte von OBI Baumarkt");
        todo5.setStatus("In Arbeit");
        todo5.setLocation("OBI");
        todo5.setUserOffered(user2);
        todo5.setUserTaken(user1);
        todo5.setExpiresAt(LocalDateTime.of(2024,6,5, 10,0));

        todoRepository.save(todo5);
    }
}
