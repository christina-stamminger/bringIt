package com.codersnextdoor.bringIt.api.user.get;

import com.codersnextdoor.bringIt.api.EmailService;
import com.codersnextdoor.bringIt.api.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class GetUserService {

    @Value("${autocreate.new.password.length}")
    private Integer PASSWORD_LENGTH;

    @Autowired
    private EmailService emailService;


    // CREATE NEW PASSWORD:
    public String generatePassword() {
        return PasswordGenerator.generateRandomPassword(PASSWORD_LENGTH);
    }

    // CREATE EMAIL TO USER:
    public void createNewLoginDataMail (User user, String newPassword) {

        String mailSubject = "bringIt - login";

        StringBuilder sbHeader = new StringBuilder();
        sbHeader.append("Es wurde ein neues Passwort für Sie generiert.\n")
                .append("Bitte verwenden sie dieses für den nächsten Login bei bringIt\n")
                .append("und ändern diesees bei Bedarf unter 'settings'.\n\n");

        StringBuilder sbLoginData = new StringBuilder();
        sbLoginData.append("Username: ").append(user.getUsername()).append("\n");
        sbLoginData.append("Password: ").append(newPassword).append("\n\n");

        StringBuilder sbFooter = new StringBuilder();
        sbFooter.append("Bitte antworten Sie nicht auf diese Nachricht.\n")
                .append("Ihr bringit-Service-Team");


        StringBuilder sbMailMessage = new StringBuilder();
        sbMailMessage.append(sbHeader).append(sbLoginData).append(sbFooter);

        String mailMessage = sbMailMessage.toString();

        emailService.sendSimpleEmail(
                user.getEmail(),
                mailSubject,
                mailMessage);

        System.out.println(" * * * * * \n"
                + "Notification-Mail with new password was sent to "
                + user.getUsername()
                + ", email: "
                + user.getEmail()
                + ".");

        }


}
