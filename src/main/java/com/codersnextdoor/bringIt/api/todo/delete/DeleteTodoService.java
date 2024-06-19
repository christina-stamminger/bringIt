package com.codersnextdoor.bringIt.api.todo.delete;

import com.codersnextdoor.bringIt.api.EmailService;
import com.codersnextdoor.bringIt.api.todo.Todo;
import com.codersnextdoor.bringIt.api.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DeleteTodoService {

    @Autowired
    private EmailService emailService;

    @Async
    public void createDeleteNotificationMail (Todo deleteTodo) {

        User userOffered = deleteTodo.getUserOffered();

        String mailSubject = "bringIt-Todo GELÖSCHT!";

        StringBuilder sbHeader = new StringBuilder();
        sbHeader.append("Das Todo '").append(deleteTodo.getTitle())
                .append("' wurde von '").append(userOffered.getUsername())
                .append("' gelöscht!").append("\n \n");

        StringBuilder sbUserBlock = new StringBuilder();
        sbUserBlock.append("User: ").append(userOffered.getUsername()).append("\n");
        sbUserBlock.append("Name: ").append(userOffered.getFirstName()).append(" ").append(userOffered.getLastName()).append("\n");
        sbUserBlock.append("Adresse: ").append(userOffered.getAddress().getStreetNumber()).append("\n")
                .append("   ").append(userOffered.getAddress().getPostalCode()).append(" ").append(userOffered.getAddress().getCity()).append("\n");
        sbUserBlock.append("Phone: ").append(userOffered.getPhone()).append("\n").append("\n \n");

        StringBuilder sbFooter = new StringBuilder();
        sbFooter.append("Bitte antworten Sie nicht auf diese Nachricht.\n")
                .append("Ihr bringit-Service-Team");

        StringBuilder sbMailMessage = new StringBuilder();
        sbMailMessage.append(sbHeader).append(sbUserBlock).append(sbFooter);
        String mailMessage = sbMailMessage.toString();

        emailService.sendSimpleEmail(
                deleteTodo.getUserTaken().getEmail(),
                mailSubject,
                mailMessage);

        System.out.println(" * * * * * \n"
                + "Notification-Mail was sent to "
                + deleteTodo.getUserTaken().getUsername()
                + ", email to: "
                + deleteTodo.getUserTaken().getEmail()
                + ".");

    }

}
