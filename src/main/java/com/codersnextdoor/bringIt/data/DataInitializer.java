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

        // Create and save user
        User user1 = new User();
        user1.setFirstName("Sarah");
        user1.setLastName("Schmidt");
        user1.setUsername("Sarah1111");
        user1.setPasswordHash("xxx");
        user1.setEmail("sarah@gmail.com");
        user1.setAddress(address1);

        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Markus");
        user2.setLastName("Müller");
        user2.setUsername("Markus12345");
        user2.setPasswordHash("yyy");
        user2.setEmail("markus@gmail.com");
        user2.setAddress(address1);

        userRepository.save(user2);

        User user3 = new User();
        user3.setFirstName("Susanne");
        user3.setLastName("Strudel");
        user3.setUsername("Susi04");
        user3.setPasswordHash("ppp");
        user3.setEmail("susi@gmail.com");
        user3.setAddress(address2);

        userRepository.save(user3);

        // Create and save todo
        Todo todo1 = new Todo();
        todo1.setCreatedAt(LocalDateTime.now());
        todo1.setTitle("Supermarktabholung");
        todo1.setDescription("Bitte um folgende Mitnahme: Roggenbrot, BioMilch 2l");
        todo1.setAddInfo("Bitte Frischmilch oder länger frisch, keine H-Milch");
        todo1.setStatus("Offen");
        todo1.setLocation("Billa, Hofer, Spar");
        todo1.setUserOffered(user1);
        todo1.setUserTaken(user3);

        todoRepository.save(todo1);
    }
}
