package com.codersnextdoor.bringIt.api.user;

import com.codersnextdoor.bringIt.api.address.Address;
import com.codersnextdoor.bringIt.api.todo.Todo;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name= "TB_USER")
public class User {

    @Id
    @GeneratedValue(generator = "user-sequence-generator")
    @GenericGenerator(
            name = "user-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "user_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "user_id")
    private long userId;

     @ManyToOne
     @JoinColumn(name = "address_id")
     private Address address;


    @OneToMany(mappedBy="userOffered")
    private Set<Todo> todosOffered;

    @OneToMany(mappedBy="userTaken")
    private Set<Todo> todosTaken;





    @Column(nullable = false, unique=true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;
    private String firstName;

    @Column(unique = true, nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private LocalDate dateOfBirth;

    private String phone;

    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //empty constructor
    public User() {

    }

    //constructor
    public User(Address address, Set<Todo> todosOffered, Set<Todo> todosTaken, String username, String passwordHash, String firstName, String lastName, String email, LocalDate dateOfBirth, String phone, LocalDateTime createdAt) {

        this.address = address;
        this.todosOffered = todosOffered;
        this.todosTaken = todosTaken;
        this.username = username;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.createdAt = createdAt;
    }


    //getter and setter
    public Set<Todo> getTodosOffered() {
        return todosOffered;
    }

    public void setTodosOffered(Set<Todo> todosOffered) {
        this.todosOffered = todosOffered;
    }

    public Set<Todo> getTodosTaken() {
        return todosTaken;
    }

    public void setTodosTaken(Set<Todo> todosTaken) {
        this.todosTaken = todosTaken;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
