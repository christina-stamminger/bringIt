package com.codersnextdoor.bringIt.api.todo.update;

import com.codersnextdoor.bringIt.api.EmailService;
import com.codersnextdoor.bringIt.api.todo.Todo;
import com.codersnextdoor.bringIt.api.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UpdateTodoService {

    @Autowired
    private EmailService emailService;

    public void createStatusNotificationMail (String todoStatus, Todo updateTodo, User userTaken) {

        String mailSubject = "";

        StringBuilder sbUserBlock = new StringBuilder();
        sbUserBlock.append("User: ").append(userTaken.getUsername()).append("\n");
        sbUserBlock.append("Name: ").append(userTaken.getFirstName()).append(" ").append(userTaken.getLastName()).append("\n");
        sbUserBlock.append("Adresse: ").append(userTaken.getAddress().getStreetNumber()).append("\n")
                .append("   ").append(userTaken.getAddress().getPostalCode()).append(" ").append(userTaken.getAddress().getCity()).append("\n");
        sbUserBlock.append("Phone: ").append(userTaken.getPhone()).append("\n").append("\n \n");

        StringBuilder sbFooter = new StringBuilder();
        sbFooter.append("Bitte antworten Sie nicht auf diese Nachricht.\n")
                .append("Ihr bringit-Service-Team");

        StringBuilder sbHeader = new StringBuilder();

        switch (todoStatus) {
            case "In Arbeit":
                mailSubject = "bringIt-Todo IN ARBEIT!";
                sbHeader.append("Ihr Todo '").append(updateTodo.getTitle())
                        .append("' wurde von '").append(userTaken.getUsername())
                        .append("' angenommen und der Status auf 'In Arbeit' geändert!").append("\n \n");
                break;
            case "Offen":
                mailSubject = "bringIt-Todo STORNIERT!";
                sbHeader.append("'").append(userTaken.getUsername()).append("' hat Ihr Todo '")
                        .append(updateTodo.getTitle()).append("' storniert.\n")
                        .append("Der Status wurde auf 'Offen' zurückgesetzt!").append("\n \n");
                break;
            case "Erledigt":
                mailSubject = "bringIt-Todo ERLEDIGT!";
                sbHeader.append("'").append(userTaken.getUsername()).append("' hat Ihr Todo '")
                        .append(updateTodo.getTitle()).append("' erledigt!\n").append("\n \n");
                break;
        }

        StringBuilder sbMailMessage = new StringBuilder();
        sbMailMessage.append(sbHeader).append(sbUserBlock).append(sbFooter);
        String mailMessage = sbMailMessage.toString();

        emailService.sendSimpleEmail(
                updateTodo.getUserOffered().getEmail(),
                mailSubject,
                mailMessage);

        System.out.println(" * * * * * \n"
                + "Notification-Mail was sent to "
                + updateTodo.getUserOffered().getUsername()
                + ", email to: "
                + updateTodo.getUserOffered().getEmail()
                + ".");
    }

}
