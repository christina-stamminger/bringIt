package com.codersnextdoor.bringIt.api.todo.create;


import com.codersnextdoor.bringIt.api.ResponseBody;
import com.codersnextdoor.bringIt.api.todo.Todo;
import com.codersnextdoor.bringIt.api.todo.TodoRepository;
import com.codersnextdoor.bringIt.api.todo.TodoResponseBody;
import com.codersnextdoor.bringIt.api.user.User;
import com.codersnextdoor.bringIt.api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/todo")
public class CreateTodoController {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;


    /**
     * CREATE NEW TODO:
     * Rest Path for POST-Request: "localhost:8081/api/todo"
     * Method creates a new todo and saves it to the database.
     *
     * @param createTodoDTO - The RequestBody should be a JSON object with the parameters:
     *                      userOfferedId (long) - id-Nr of User creating the todo,
     *                      title (String, NOT NULL) - Todo-Title,
     *                      location (String) - The location of the todo (i.e. pickup-place, store, etc.),
     *                      description (String) - Todo-description,
     *                      addInfo (String) - Additional Info for Todo,
     *                      expiresAt (LocalDateTime) - Expiry Date and Time of the Todo in the form: yyyy-mm-ddThh:mm
     *
     * @return - ResponseBody incl. the newly created and saved todo, a message or errorMessage and StatusCode
     *         - Everything correct: todo, success-message, StatusCode 200 (OK)
     *  possible Exceptions and StatusCodes:
     *  - Requestbody or Title is empty: errorMessage, StatusCode 400 (BAD_REQUEST)
     *  - UserOffered doesn't exist: errorMessage, StatusCode 404 (NOT_FOUND)
     *  - Todo allready exists: errorMessage, StatusCode 409 (CONFLICT)
     *  - expiredAt is before current DateTime: errorMessage, StatusCode 400 (BAD_REQUEST)
     *  - Todo could not be saved to database: errorMessage, StatusCode 500 (INTERNAL_SERVER_ERROR)
     */
    @PostMapping
    public ResponseEntity<ResponseBody> newTodo(
            @RequestBody
            CreateTodoDTO createTodoDTO) {

        TodoResponseBody todoResponseBody = new TodoResponseBody();


        // CHECK IF REQUEST_BODY IS EMPTY:
        if (createTodoDTO == null) {
            todoResponseBody.addErrorMessage("Requestbody is empty.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.BAD_REQUEST);
        }


        // CHECK IF USER_OFFERED EXISTS:
        Optional<User> optionalUser = userRepository.findByUserId(createTodoDTO.getUserOfferedId());
        if (optionalUser.isEmpty()) {
            todoResponseBody.addErrorMessage("User with id " + createTodoDTO.getUserOfferedId() + " doesn't exist.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.NOT_FOUND);
        }

        User userOffered = optionalUser.get();


        // CHECK IF TITLE IS PROVIDED:
        if (createTodoDTO.getTitle().isEmpty() || createTodoDTO.getTitle() == null) {
            todoResponseBody.addErrorMessage("Titel is missing in Requestbody.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.BAD_REQUEST);
        }


        // CHECK IF EXACT SAME TODO ALREADY EXISTS:
        Optional<Todo> optionalTodo = todoRepository.findByUserOfferedAndLocationAndTitleAndDescriptionAndAddInfoAndExpiresAt(
                userOffered,
                createTodoDTO.getLocation(),
                createTodoDTO.getTitle(),
                createTodoDTO.getDescription(),
                createTodoDTO.getAddInfo(),
                createTodoDTO.getExpiresAt()
        );
        if (optionalTodo.isPresent()) {
            todoResponseBody.addErrorMessage("This Todo already exists with id " + optionalTodo.get().getTodoId() + ".");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.CONFLICT);
        }


        // CHECK IF EXPIRED_AT IS VALID:
        if (createTodoDTO.getExpiresAt().isBefore(LocalDateTime.now())) {
            todoResponseBody.addErrorMessage("ExpiredAt is before current DateTime! The instant of time must be in future.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.BAD_REQUEST);
        }


        // CREATE AND SAVE NEW TODO OBJECT:
        Todo newTodo = new Todo(
                userOffered,
                null,
                createTodoDTO.getLocation(),
                createTodoDTO.getTitle(),
                createTodoDTO.getDescription(),
                createTodoDTO.getAddInfo(),
                createTodoDTO.getUploadPath(),
                LocalDateTime.now(),
                createTodoDTO.getExpiresAt(),
                "Offen"
        );
        todoRepository.save(newTodo);


        // CHECK IF TODO WAS SAVED IN DATABASE:
        optionalTodo = todoRepository.findById(newTodo.getTodoId());
        if (optionalTodo.isEmpty()) {
            todoResponseBody.addErrorMessage("Todo couldn't be saved.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        // RETURN NEW TODO:
        todoResponseBody.setTodo(newTodo);
        todoResponseBody.addMessage("Todo was created successfully");
        return ResponseEntity.ok(todoResponseBody);

    }
}
