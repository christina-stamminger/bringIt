package com.codersnextdoor.bringIt.api.todo;

import com.codersnextdoor.bringIt.api.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;


@Entity
    @Table(name= "TB_TODO")
    public class Todo {

        @Id
        @GeneratedValue(generator = "todo-sequence-generator")
        @GenericGenerator(
                name = "todo-sequence-generator",
                strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
                parameters = {
                        @org.hibernate.annotations.Parameter(name = "sequence_name", value = "todo_sequence"),
                        @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                        @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
                }
        )
        private long todoId;

    @ManyToOne
    @JoinColumn(name = "useroffered_id", referencedColumnName = "user_id")
    private User userOffered;

    @ManyToOne
    @JoinColumn(name = "usertaken_id", referencedColumnName = "user_id")
    private User userTaken;



        private String location;

        private String title;

        private String description;

        private String addInfo;

        private String uploadPath;

        private LocalDateTime createdAt;


        // @Column(nullable = false)
        private LocalDateTime expiresAt;

        private String status;


        // empty constructor
        public Todo() {

        }

        // constructor

    public Todo(User userOffered, User userTaken, String location, String title, String description, String addInfo, String uploadPath, LocalDateTime createdAt, LocalDateTime expiresAt, String status) {

        this.userOffered = userOffered;
        this.userTaken = userTaken;
        this.location = location;
        this.title = title;
        this.description = description;
        this.addInfo = addInfo;
        this.uploadPath = uploadPath;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.status = status;
    }


    //getter and setter

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUserTaken() {
        return userTaken;
    }

    public void setUserTaken(User userTaken) {
        this.userTaken = userTaken;
    }

    public User getUserOffered() {
        return userOffered;
    }

    public void setUserOffered(User userOffered) {
        this.userOffered = userOffered;
    }




}
