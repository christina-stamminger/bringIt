package com.codersnextdoor.bringIt.data;

import com.codersnextdoor.bringIt.api.address.Address;
import com.codersnextdoor.bringIt.api.address.AddressRepository;
import com.codersnextdoor.bringIt.api.todo.Todo;
import com.codersnextdoor.bringIt.api.todo.TodoRepository;
import com.codersnextdoor.bringIt.api.user.User;
import com.codersnextdoor.bringIt.api.user.UserRepository;
import com.codersnextdoor.bringIt.api.user.create.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
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
    public PasswordEncoder passwordEncoder;

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

        Address address5 = new Address(
                "Pötzleinsdorferstrasse 79",
                "1180",
                "Wien"
        );
        addressRepository.save(address5);

        Address address6 = new Address(
                "Khekgasse 17",
                "1230",
                "Wien"
        );
        addressRepository.save(address6);

        Address address7 = new Address(
                "Leopold-Böhm-Straße 5",
                "1030",
                "Wien"
        );
        addressRepository.save(address7);

        Address address8 = new Address(
                "Erdbergerstraße 218",
                "1030",
                "Wien"
        );
        addressRepository.save(address8);

        Address address9 = new Address(
                "Medwedweg 14",
                "1110",
                "Wien"
        );
        addressRepository.save(address9);

        Address address10 = new Address(
                "Kopalgasse 60",
                "1110",
                "Wien"
        );
        addressRepository.save(address10);

        Address address11 = new Address(
                "Wilhelm-Weber-Weg 4",
                "1110",
                "Wien"
        );
        addressRepository.save(address11);

        Address address12 = new Address(
                "Holzergasse 9",
                "1110",
                "Wien"
        );
        addressRepository.save(address12);

        Address address13 = new Address(
                "Lechnerstrasse 6",
                "1030",
                "Wien"
        );
        addressRepository.save(address13);

        Address address14 = new Address(
                "Landsgtraßer Hauptstraße 83-85",
                "1030",
                "Wien"
        );
        addressRepository.save(address14);

        // Create and save user
        User user1 = new User();
        user1.setFirstName("Sarah");
        user1.setLastName("Schmidt");
        user1.setDateOfBirth(LocalDate.of(1990, 5,10));
        user1.setUsername("Sarah1111");
        //user1.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user1.setPassword(passwordEncoder.encode("test")); // Hash the password

        user1.setEmail("sarah@gmail.com");
        user1.setPhone("+43676444818027");
        user1.setAddress(address1);
        user1.setBringIts(22);

        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Markus");
        user2.setLastName("Müller");
        user2.setDateOfBirth(LocalDate.of(1980, 5,13));
        user2.setUsername("Markus12345");
        user2.setPassword(passwordEncoder.encode("test")); // Hash the password
        user2.setEmail("markus@gmail.com");
        user2.setPhone("+436508280857");
        user2.setAddress(address7);
        user2.setBringIts(4);

        userRepository.save(user2);

        User user3 = new User();
        user3.setFirstName("Susanne");
        user3.setLastName("Strudel");
        user3.setDateOfBirth(LocalDate.of(1974, 9,24));
        user3.setUsername("Susi04");
        user3.setPassword(passwordEncoder.encode("test")); // Hash the password
        user3.setEmail("susi@gmail.com");
        user3.setPhone("+436643475661");
        user3.setAddress(address2);
        user3.setBringIts(34);

        userRepository.save(user3);

        User user4 = new User(
                address8,
                "mariadb",
                passwordEncoder.encode("test"),
                "Maria",
                "Dingsbums",
                LocalDate.of(1999, 8,7),
                "mariadb@yahoo.com",
                "+436501209348756",
                41
        );
        userRepository.save(user4);

        User user5 = new User(
                address4,
                "toni",
                passwordEncoder.encode("test"),
                "Anton",
                "Nimmtgernmit",
                LocalDate.of(2002, 3,4),
                "toni2002@gmail.com",
                "+4365000998877112",
                15
        );
        userRepository.save(user5);

        User user6 = new User(
                address6,
                "franzi",
                passwordEncoder.encode("test"),
                "Franziska",
                "Otto",
                LocalDate.of(2000, 1,1),
                "franziskaotto11@gmail.com",
                "+436707731405",
                79
        );
        userRepository.save(user6);

        User user7 = new User(
                address5,
                "hansi",
                passwordEncoder.encode("test"),
                "Hannes",
                "Kurth",
                LocalDate.of(2000, 2,2),
                "donnerknecht@gmail.com",
                "+436504792527",
                57
        );
        userRepository.save(user7);

        User user8 = new User(
                address3,
                "chris",
                passwordEncoder.encode("test"),
                "Christina",
                "Stamminger",
                LocalDate.of(2000, 3,3),
                "christina.stamminger@gmail.com",
                "+436707731405",
                38
        );
        userRepository.save(user8);

        User user9 = new User(
                address9,
                "hadschibradschi",
                passwordEncoder.encode("test"),
                "Holger",
                "Grausam",
                LocalDate.of(1995, 2,17),
                "holger.grausam@gmail.com",
                "+436645598762",
                3
        );
        userRepository.save(user9);

        User user10 = new User(
                address10,
                "nudelking",
                passwordEncoder.encode("test"),
                "Konrad",
                "Nudel",
                LocalDate.of(1997, 12,14),
                "nudelking@gmail.com",
                "+436509182736",
                30
        );
        userRepository.save(user10);

        User user11 = new User(
                address11,
                "samariter",
                passwordEncoder.encode("test"),
                "Samuel",
                "Ritter",
                LocalDate.of(2002, 11,24),
                "sam.ritter@yahoo.com",
                "+436617685940",
                64
        );
        userRepository.save(user11);

        User user12 = new User(
                address12,
                "mjam-mjam",
                passwordEncoder.encode("test"),
                "Miriam",
                "Jammer",
                LocalDate.of(2004, 10,2),
                "mjam@yahoo.com",
                "+436636336522",
                4
        );
        userRepository.save(user12);

        User user13 = new User(
                address13,
                "käseglocker",
                passwordEncoder.encode("test"),
                "Gesine",
                "Glöckner",
                LocalDate.of(2005, 9,20),
                "gesine0509@outlook.com",
                "+436605504403",
                49
        );
        userRepository.save(user13);

        User user14 = new User(
                address14,
                "nichtweitweg",
                passwordEncoder.encode("test"),
                "Waltraud",
                "Niessner",
                LocalDate.of(2007, 8,11),
                "nichtweit-07@outlook.com",
                "+436601020304",
                104
        );
        userRepository.save(user14);

        User user15 = new User(
                address12,
                "wohntauchda",
                passwordEncoder.encode("test"),
                "Wilhelmine",
                "Taucher",
                LocalDate.of(2008, 6,30),
                "wt-wtd20080630@outlook.com",
                "+4366090080023",
                22
        );
        userRepository.save(user15);



        // Create and save todo
        Todo todo1 = new Todo();
        todo1.setTitle("Supermarktabholung");
        todo1.setDescription("Bitte um folgende Mitnahme: Roggenbrot, BioMilch 2l");
        todo1.setAddInfo("Bitte Frischmilch oder länger frisch, keine H-Milch");
        todo1.setStatus("In Arbeit");
        todo1.setLocation("Billa, Hofer, Spar");
        todo1.setUserOffered(user1);
        todo1.setExpiresAt(LocalDateTime.of(2024,7,22, 10,0));
        todo1.setUserTaken(user3);

        todoRepository.save(todo1);

        Todo todo2 = new Todo();
        todo2.setTitle("Apothekenabholung");
        todo2.setDescription("Bitte um folgende Abholung: Mexalen 500mg");
        todo2.setAddInfo("Bereits bezahlt, wurde bestellt ist abholbereit");
        todo2.setStatus("Offen");
        todo2.setLocation("Apotheke");
        todo2.setUserOffered(user3);
        todo2.setExpiresAt(LocalDateTime.of(2024,7,24, 10,0));

        todoRepository.save(todo2);

        Todo todo3 = new Todo();
        todo3.setTitle("Drogerie");
        todo3.setDescription("Bitte um folgende Besorgung: Milchpulver PRE1 Babynahrung");
        todo3.setAddInfo("Bitte von Hipp, Alternative DM Eigenmarke");
        todo3.setStatus("Offen");
        todo3.setLocation("DM, Bipa, Müller");
        todo3.setUserOffered(user4);
        todo3.setExpiresAt(LocalDateTime.of(2024,7,16, 10,0));

        todoRepository.save(todo3);

        Todo todo4 = new Todo();
        todo4.setTitle("Supermarkt");
        todo4.setDescription("Bitte um folgende Besorgung: 1kg Bio Äpfel, 2 Gurken, Ziegenkäse");
        todo4.setAddInfo("Bitte Bio, wenn nicht schön /vorhanden dann normal");
        todo4.setStatus("Offen");
        todo4.setLocation("Billa, Hofer, Lidl,Spar");
        todo4.setUserOffered(user2);
        todo4.setExpiresAt(LocalDateTime.of(2024,7,21, 10,0));

        todoRepository.save(todo4);

        Todo todo5= new Todo();
        todo5.setTitle("Baumarkt");
        todo5.setDescription("Bitte um folgende Besorgung: 1 Kartusche Silikon grau");
        todo5.setAddInfo("Bitte von OBI Baumarkt");
        todo5.setStatus("In Arbeit");
        todo5.setLocation("OBI");
        todo5.setUserOffered(user5);
        todo5.setUserTaken(user1);
        todo5.setExpiresAt(LocalDateTime.of(2024,7,25, 10,0));

        todoRepository.save(todo5);

        Todo todo6 = new Todo (
                user6,
                user7,
                "Markt",
                "Lebensmittel",
                "12 Eier vom Markt mitnehmen",
                "Markt oder Bauer oder Supermarkt",
                null,
                LocalDateTime.of(2024,8,1,10,0),
                "In Arbeit"
        );
        todoRepository.save(todo6);

        Todo todo7 = new Todo (
                user8,
                null,
                "Bauer",
                "Lebensmittel",
                "Äpfel",
                "Bitte nicht vom Supermarkt",
                null,
                LocalDateTime.of(2024,7,20,16,0),
                "Offen"
        );
        todoRepository.save(todo7);

        Todo todo8 = new Todo (
                user7,
                null,
                "Pensionistenwohnheim sowieso",
                "Packerl von Pensionistenheim abholen",
                "Packerl für Familie Schulz",
                "Ein Papiersack, ca 3 kg",
                null,
                LocalDateTime.of(2024,7,23,17,30),
                "Offen"
        );
        todoRepository.save(todo8);

        Todo todo9 = new Todo (
                user9,
                null,
                "Sportgeschäft",
                "Rollen für Scooter ",
                "2 Neue Rollen für Scooter (Micro) ",
                "Habe mit Geschäft telefoniert. Verkäufer weiss bescheid. Unter Namen 'Grausem' hinterlegt.",
                null,
                LocalDateTime.of(2024,7,22,14,0),
                "Offen"
        );
        todoRepository.save(todo9);

        Todo todo10 = new Todo (
                user10,
                null,
                "AsiaResaturant oder ähnliches",
                "Nudeln mit Hühnerfleisch",
                "Eine Portion gebackene Nudeln mit Hühnerfleisch und Ei",
                "Soll maximal 10 EUro kosten",
                null,
                LocalDateTime.of(2024,7,20,15,20),
                "Offen"
        );
        todoRepository.save(todo10);

        Todo todo11 = new Todo (
                user11,
                null,
                "Drogeriemarkt oder Supermark",
                "Windeln",
                "1 Pkg Pampers Baby Dry, Gr.4",
                "Wenn Größe 4 nicht vorhanden, bitte kurz anrufen",
                null,
                LocalDateTime.of(2024,7,21,10,10),
                "Offen"
        );
        todoRepository.save(todo11);

        Todo todo12 = new Todo (
                user12,
                null,
                "Supermarkt, Tankstelle, Markt",
                "Grillgut",
                "Würstel und/oder FLeisch zum Grillen für 4 Personen",
                "Keine Käsekreiner, sonst alles gut",
                null,
                LocalDateTime.of(2024,7,21,19,0),
                "Offen"
        );
        todoRepository.save(todo12);

        Todo todo13 = new Todo (
                user13,
                null,
                "Hofer oder Billa",
                "Mehl und Germ",
                "Bitte 1 Pkg frischen Germ und 2 kg Dinkelvollkornmehl",
                null,
                null,
                LocalDateTime.of(2024,7,24,12,0),
                "Offen"
        );
        todoRepository.save(todo13);

        Todo todo14 = new Todo (
                user14,
                null,
                null,
                "Bier",
                "1 6er-tragerl Bier",
                "Stiegel oder Gösser",
                null,
                LocalDateTime.of(2024,7,23,23,0),
                "Offen"
        );
        todoRepository.save(todo14);

        Todo todo15 = new Todo (
                user15,
                null,
                null,
                "Hafermilch",
                "bitte 2 Liter Hafermilch mitnehmen",
                "wenn möglich Barista-Hafermilch",
                null,
                LocalDateTime.of(2024,7,22,9,0),
                "Offen"
        );
        todoRepository.save(todo15);

        Todo todo16 = new Todo (
                user3,
                null,
                null,
                "Joghurt",
                "bitte 2 grosse Pkg Joghurt natur (je 1/2 kg)",
                null,
                null,
                LocalDateTime.of(2024,7,15,12,0),
                "Abgelaufen"
        );
        todoRepository.save(todo16);

        Todo todo17 = new Todo (
                user3,
                user10,
                "Supermarkt oder Bäcker",
                "Brot",
                "bitte 1 Kg Roggenbrot mitnehmen",
                null,
                null,
                LocalDateTime.of(2024,7,19,18,0),
                "Erledigt"
        );
        todoRepository.save(todo17);

        // Some more Data by ChatGPT:

        // Create and save additional addresses
        Address address18 = new Address("Guglgasse 15", "1110", "Wien");
        Address address19 = new Address("Kanalwächterhausweg 5", "1020", "Wien");
        Address address20 = new Address("Schnirchgasse 9", "1030", "Wien");
        Address address21 = new Address("Modecenterstrasse 1", "1030", "Wien");
        Address address22 = new Address("Holzergasse 3", "1110", "Wien");
        Address address23 = new Address("Kanikgasse 38", "1110", "Wien");
        Address address24 = new Address("Grillgasse 43", "1110", "Wien");
        Address address25 = new Address("Dietrichgasse 59", "1030", "Wien");
        Address address26 = new Address("Baumschulstraßsse 2", "3013", "Tullnerbach-Lawies");
        Address address27 = new Address("Badgasse 11", "3013", "Pressbaum");

        addressRepository.saveAll(List.of(address18, address19, address20, address21, address22, address23, address24, address25, address26, address27));

        // Create and save additional users
        User user18 = new User(address18, "thomasbauer", passwordEncoder.encode("test"), "Thomas", "Bauer", LocalDate.of(1993, 4, 15), "thomas.bauer@gmail.com", "+436645509876", 10);
        User user19 = new User(address19, "anna88", passwordEncoder.encode("test"), "Anna", "Müller", LocalDate.of(1988, 7, 22), "anna.mueller@gmail.com", "+436645509877", 12);
        User user20 = new User(address20, "lukas.klein", passwordEncoder.encode("test"), "Lukas", "Klein", LocalDate.of(1995, 11, 11), "lukas.klein@gmail.com", "+436645509878", 15);
        User user21 = new User(address21, "laura.fischer", passwordEncoder.encode("test"), "Laura", "Fischer", LocalDate.of(1990, 9, 29), "laura.fischer@gmail.com", "+436645509879", 20);
        User user22 = new User(address22, "peter.schmidt", passwordEncoder.encode("test"), "Peter", "Schmidt", LocalDate.of(1985, 1, 4), "peter.schmidt@gmail.com", "+436645509880", 25);
        User user23 = new User(address23, "julia.wagner", passwordEncoder.encode("test"), "Julia", "Wagner", LocalDate.of(1992, 3, 17), "julia.wagner@gmail.com", "+436645509881", 30);
        User user24 = new User(address24, "daniel.schwarz", passwordEncoder.encode("test"), "Daniel", "Schwarz", LocalDate.of(1991, 6, 30), "daniel.schwarz@gmail.com", "+436645509882", 35);
        User user25 = new User(address25, "melanie.weber", passwordEncoder.encode("test"), "Melanie", "Weber", LocalDate.of(1987, 8, 18), "melanie.weber@gmail.com", "+436645509883", 40);
        User user26 = new User(address26, "alex.braun", passwordEncoder.encode("test"), "Alexander", "Braun", LocalDate.of(1989, 12, 12), "alex.braun@gmail.com", "+436645509884", 45);
        User user27 = new User(address27, "stephanie.mayer", passwordEncoder.encode("test"), "Stephanie", "Mayer", LocalDate.of(1994, 10, 10), "stephanie.mayer@gmail.com", "+436645509885", 50);

        userRepository.saveAll(List.of(user18, user19, user20, user21, user22, user23, user24, user25, user26, user27));

        // Create and save additional todos
        Todo todo18 = new Todo(
                user18,
                null,
                "Supermarktbesuch",
                "Bitte um folgende Mitnahme: 1 Packung Kaffee, 1 Packung Zucker",
                "Keine speziellen Marken",
                null,
                null,
                LocalDateTime.of(2024, 7, 25, 10, 0),
                "Offen"
        );
        Todo todo19 = new Todo(
                user19,
                null,
                "Medikamentenabholung",
                "Bitte Mexalen 500mg abholen",
                "Bereits bezahlt, abholbereit",
                null,
                null,
                LocalDateTime.of(2024, 7, 26, 10, 0),
                "Offen"
        );
        Todo todo20 = new Todo(
                user20,
                null,
                "Drogeriebesuch",
                "Bitte 2 Flaschen Shampoo besorgen",
                "Marken egal, bitte keine Sonderaktionen",
                null,
                null,
                LocalDateTime.of(2024, 7, 27, 10, 0),
                "Offen"
        );
        Todo todo21 = new Todo(
                user21,
                null,
                "Obst und Gemüse",
                "Bitte Äpfel und Karotten mitbringen",
                "Bio bevorzugt",
                null,
                null,
                LocalDateTime.of(2024, 7, 28, 10, 0),
                "Offen"
        );
        Todo todo22 = new Todo(
                user22,
                null,
                "Baustellenbedarf",
                "1 Packung Nägel und 1 Hammer",
                "Normale Größe",
                null,
                null,
                LocalDateTime.of(2024, 7, 29, 10, 0),
                "Offen"
        );
        Todo todo23 = new Todo(
                user23,
                null,
                "Getränkeeinkauf",
                "1 Kasten Mineralwasser",
                "Keine speziellen Anforderungen",
                null,
                null,
                LocalDateTime.of(2024, 7, 30, 10, 0),
                "Offen"
        );
        Todo todo24 = new Todo(
                user24,
                null,
                "Packerl abholen",
                "Packerl für Familie Schmidt",
                "Abholung bei der Postfiliale",
                null,
                null,
                LocalDateTime.of(2024, 7, 31, 10, 0),
                "Offen"
        );
        Todo todo25 = new Todo(
                user25,
                null,
                "Tierbedarf",
                "Katzenfutter und Katzenstreu besorgen",
                "Marken egal, bitte keine Sonderaktionen",
                null,
                null,
                LocalDateTime.of(2024, 8, 1, 10, 0),
                "Offen"
        );
        Todo todo26 = new Todo(
                user26,
                null,
                "Backzutaten",
                "1 kg Mehl und 1 Packung Backpulver",
                "Marken egal",
                null,
                null,
                LocalDateTime.of(2024, 8, 2, 10, 0),
                "Offen"
        );
        Todo todo27 = new Todo(
                user27,
                null,
                "Grillzutaten",
                "Würstchen und Ketchup besorgen",
                "Keine speziellen Marken",
                null,
                null,
                LocalDateTime.of(2024, 8, 3, 10, 0),
                "Offen"
        );

        todoRepository.saveAll(List.of(todo18, todo19, todo20, todo21, todo22, todo23, todo24, todo25, todo26, todo27));



    }
}
