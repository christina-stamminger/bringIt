package com.codersnextdoor.bringIt.api.user.delete;

import com.codersnextdoor.bringIt.api.EmailService;
import com.codersnextdoor.bringIt.api.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserService {

    @Autowired
    private EmailService emailService;

    @Async
    public void createNotificationMailToUserTaken (User userTaken) {


    }





    @Async
    public void createNotificationMailToUserOffered (User userOffered) {


    }

}
