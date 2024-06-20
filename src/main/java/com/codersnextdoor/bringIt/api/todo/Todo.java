package com.codersnextdoor.bringIt.api.todo;

import com.codersnextdoor.bringIt.api.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;
import static org.hibernate.annotations.OnDeleteAction.SET_NULL;


// CHECK IF EMAIL IS IN EMAIL-FORMAT:
//
// public static final String regexPatternEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
//
//   if (!email.matches(regexPatternEmail)) { ... excepteion ... }
//

@Setter
@Getter
@Entity
@Table(name = "TB_TODO")
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
    @OnDelete(action = CASCADE)
    @JoinColumn(name = "useroffered_id", referencedColumnName = "user_id")
    private User userOffered;

    @ManyToOne
    @OnDelete(action = SET_NULL)
    @JoinColumn(name = "usertaken_id", referencedColumnName = "user_id")
    private User userTaken;


    private String location;

    @Column(nullable = false)
    private String title;

    private String description;

    private String addInfo;

    private String uploadPath;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private String status;


    // empty constructor
    public Todo() {

    }

    // constructor

    public Todo(
            User userOffered,
            User userTaken,
            String location,
            String title,
            String description,
            String addInfo,
            String uploadPath,
            LocalDateTime expiresAt,
            String status) {

        this.userOffered = userOffered;
        this.userTaken = userTaken;
        this.location = location;
        this.title = title;
        this.description = description;
        this.addInfo = addInfo;
        this.uploadPath = uploadPath;
        this.expiresAt = expiresAt;
        this.status = status;
    }


    //getter and setter by lombok


}
