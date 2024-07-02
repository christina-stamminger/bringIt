package com.codersnextdoor.bringIt.api.todo.update;

import com.codersnextdoor.bringIt.api.todo.Todo;
import com.codersnextdoor.bringIt.api.todo.TodoRepository;
import com.codersnextdoor.bringIt.api.todo.TodoResponseBody;
import com.codersnextdoor.bringIt.api.user.User;
import com.codersnextdoor.bringIt.api.user.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/todo")
public class UpdateTodoController {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UpdateTodoService updateTodoService;

    @Value("${add.bringIts.for.accept}")
    private Integer ADD_BRINGITS_FOR_ACCEPT;
    @Value("${add.bringIts.for.complete}")
    private Integer ADD_BRINGITS_FOR_COMPLETE;


    /**
     * UPDATE TODO
     * Rest Path for PUT-Request: "localhost:8081/api/todo"
     * Method finds an existing todo by id and updates it with the provided values from the Requestbody.
     *
     * @param updateTodoDTO - The RequestBody should be a JSON object with at least one of these parameters:
     *                      todoId (long) - id-Nr of Todo to be updated,
     *                      title (String, NOT NULL) - Todo-Title,
     *                      location (String) - The location of the todo (i.e. pickup-place, store, etc.),
     *                      description (String) - Todo-description,
     *                      addInfo (String) - Additional Info for Todo,
     *                      expiresAt (LocalDateTime) - Expiry Date and Time of the Todo in the form: yyyy-mm-ddThh:mm
     *
     * @return  - ResponseBody incl. the updated and saved todo, a message or errorMessage and StatusCode
     *          - Everything correct: todo, success-message, StatusCode 200 (OK)
     * possible Exceptions and StatusCodes:
     * - Requestbody is empty: errorMessage, StatusCode 400 (BAD_REQUEST)
     * - Todo doesn't exist: errorMessage, StatusCode 404 (NOT_FOUND)
     * - expiredAt is before current DateTime: errorMessage, StatusCode 400 (BAD_REQUEST)
     * - Updated of Todo was not successfull: errorMessage, StatusCode 500 (INTERNAL_SERVER_ERROR)
     */
    @PutMapping
    public ResponseEntity<TodoResponseBody> updateTodo(
            @RequestBody
            UpdateTodoDTO updateTodoDTO) {

        TodoResponseBody todoResponseBody = new TodoResponseBody();


        // CHECK IF REQUEST_BODY IS EMPTY:
        if (updateTodoDTO == null) {
            todoResponseBody.addErrorMessage("Requestbody is empty.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.BAD_REQUEST);
        }


        // CHECK IF TODO EXISTS:
        Optional<Todo> optionalTodo = todoRepository.findById(updateTodoDTO.getTodoId());
        if (optionalTodo.isEmpty()) {
            todoResponseBody.addErrorMessage("Todo with id " + updateTodoDTO.getTodoId() + " doesn't exist.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.NOT_FOUND);
        }
        Todo updateTodo = optionalTodo.get();


        // CHECK IF EXPIRED_AT IS VALID:
        if (!ObjectUtils.isEmpty(updateTodoDTO.getExpiresAt()) && updateTodoDTO.getExpiresAt().isBefore(LocalDateTime.now())) {
            todoResponseBody.addErrorMessage("ExpiredAt is before current DateTime! The instant of time must be in future.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.BAD_REQUEST);
        }


        // UPDATE VALUES THAT ARE PROVIDED BY UpdateTodoDTO:
        if (!StringUtils.isEmpty(updateTodoDTO.getTitle())) {
            updateTodo.setTitle(updateTodoDTO.getTitle());
        }
        if (!StringUtils.isEmpty(updateTodoDTO.getLocation())) {
            updateTodo.setLocation(updateTodoDTO.getLocation());
        }
        if (!StringUtils.isEmpty(updateTodoDTO.getDescription())) {
            updateTodo.setDescription(updateTodoDTO.getDescription());
        }
        if (!StringUtils.isEmpty(updateTodoDTO.getAddInfo())) {
            updateTodo.setAddInfo(updateTodoDTO.getAddInfo());
        }
        if (!StringUtils.isEmpty(updateTodoDTO.getUploadPath())) {
            updateTodo.setUploadPath(updateTodoDTO.getUploadPath());
        }
        if (!ObjectUtils.isEmpty(updateTodoDTO.getExpiresAt())) {
            updateTodo.setExpiresAt(updateTodoDTO.getExpiresAt());
        }


        // SAVE THE UPDATED TODO:
        Todo savedTodo = todoRepository.save(updateTodo);


        // VERIFY IF TODO WAS SAVED CORRECTLY:
        Optional<Todo> optionalSavedTodo = todoRepository.findById(updateTodoDTO.getTodoId());
        if (optionalSavedTodo.isPresent()) {
            Todo todoInDb = optionalSavedTodo.get();
            if (!todoInDb.equals(savedTodo)) {
                todoResponseBody.addErrorMessage("Update of Todo with id " + updateTodoDTO.getTodoId() + " was not successful!");
                return new ResponseEntity<>(todoResponseBody, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            todoResponseBody.addErrorMessage("Updated Todo with id " + updateTodoDTO.getTodoId() + " was not found after saving!");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        // RETURN UPDATED TODO:
        todoResponseBody.setTodo(savedTodo);
        todoResponseBody.addMessage("Todo with id " + updateTodoDTO.getTodoId() + " was successfully updated!");
        return new ResponseEntity<>(todoResponseBody, HttpStatus.OK);
    }


    /**
     * UPDATE TODO-STATUS:
     * Rest Path for PUT-Request: "localhost:8081/api/todo/status"
     * Method finds existing todo by id and updates its status and userTaken with the provided values from the Requestbody
     * and generates a Notification-Mail to the userOffered of this todo.
     *
     * @param updateTodoStatusDTO - The RequestBody should be a JSON object with these parameters:
     *                            - todoId (long) - id-Nr of Todo to be updated,
     *                            - userTakenId (long) - id-Nr of wants to/already accept(ed) the Todo
     *                            - status (String) - Only one of the following options possible:
     *                              "Offen", "In Arbeit", "Erledigt"
     *
     * @return - ResponseBody incl. the updated and saved todo, a message or errorMessage and StatusCode
     *         - Everything correct: todo, success-message, StatusCode 200 (OK)
     *         - Exceptions: ErrorMessage and HttpStatus 400, 404 or 409
     */
    @PatchMapping("/status")
    public ResponseEntity<TodoResponseBody> updateTodoStatus(
            @RequestBody
            UpdateTodoStatusDTO updateTodoStatusDTO) {


        TodoResponseBody todoResponseBody = new TodoResponseBody();



        // CHECK IF REQUEST_BODY IS EMPTY:
        if (updateTodoStatusDTO == null) {
            todoResponseBody.addErrorMessage("Requestbody is empty.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.BAD_REQUEST);
        }


        // CHECK IF TODO EXISTS:
        Optional<Todo> optionalTodo = todoRepository.findById(updateTodoStatusDTO.getTodoId());
        if (optionalTodo.isEmpty()) {
            todoResponseBody.addErrorMessage("Todo with id " + updateTodoStatusDTO.getTodoId() + " doesn't exist.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.NOT_FOUND);
        }
        Todo updateTodo = optionalTodo.get();

        System.out.println("updateTodoStatusDTO: " + updateTodoStatusDTO.getTodoId() + " / "
                + updateTodoStatusDTO.getUserTakenId() + " / "
                + updateTodoStatusDTO.getStatus());

        // VALIDATE USER_TAKEN:
        if (ObjectUtils.isEmpty(updateTodoStatusDTO.getUserTakenId()))  {
            todoResponseBody.addErrorMessage("userTakenId is missing in RequestBody.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.BAD_REQUEST);
        }
        if (!ObjectUtils.isEmpty(updateTodoStatusDTO.getUserTakenId()) &&
                updateTodoStatusDTO.getUserTakenId() == updateTodo.getUserOffered().getUserId()) {
            todoResponseBody.addErrorMessage("UserTaken has to be different to UserOffered.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.CONFLICT);
        }
        Optional<User> optionalUser = userRepository.findByUserId(updateTodoStatusDTO.getUserTakenId());
        if (optionalUser.isEmpty()) {
            todoResponseBody.addErrorMessage("User with id " + updateTodoStatusDTO.getUserTakenId() + " doesn't exist.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.NOT_FOUND);
        }
        User userTaken = optionalUser.get();


        // VALIDATE STATUS:
        if (StringUtils.isEmpty(updateTodoStatusDTO.getStatus())) {
            todoResponseBody.addErrorMessage("Status is missing in RequestBody");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.BAD_REQUEST);
        }
        if (!updateTodoStatusDTO.getStatus().equals("In Arbeit")
                && !updateTodoStatusDTO.getStatus().equals("Offen")
                && !updateTodoStatusDTO.getStatus().equals("Erledigt")) {
            todoResponseBody.addErrorMessage("Status can only be set to 'In Arbeit' or 'Offen' or 'Erledigt'.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.BAD_REQUEST);
        }
        if (updateTodo.getUserTaken() == null && !updateTodoStatusDTO.getStatus().equals("In Arbeit")) {
            todoResponseBody.addErrorMessage("A Todo with status 'Offen' can only be set to 'In Arbeit'.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.BAD_REQUEST);
        }
        if ((updateTodoStatusDTO.getStatus().equals("Erledigt") || updateTodoStatusDTO.getStatus().equals("Offen"))
                && !updateTodo.getStatus().equals("In Arbeit")) {
            todoResponseBody.addErrorMessage("Status can only be set to 'Erledigt' or 'Offen' if it is 'In Arbeit'.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.BAD_REQUEST);
        }

        // CHANGE BringIts AMOUNT FOR USER:
        String updateTodoStatus = updateTodoStatusDTO.getStatus();
        switch (updateTodoStatus) {
            case "Offen":
                updateTodoService.changeBringItsAmount(userTaken, ((-1) * ADD_BRINGITS_FOR_ACCEPT));
                break;
            case "In Arbeit":
                updateTodoService.changeBringItsAmount(userTaken, ADD_BRINGITS_FOR_ACCEPT);
                break;
            case "Erledigt":
                updateTodoService.changeBringItsAmount(userTaken, ADD_BRINGITS_FOR_COMPLETE);
                break;
        }

        // SEND EMAIL NOTIFICATION TO USER_OFFERED:
        updateTodoService.createStatusNotificationMail(updateTodoStatusDTO.getStatus(), updateTodo, userTaken);

        // CHANGE STATUS AND USER_TAKEN / SAVE:
        if (updateTodoStatusDTO.getStatus().equals("Offen")) {
            userTaken = null;
        }

        updateTodo.setStatus(updateTodoStatusDTO.getStatus());
        updateTodo.setUserTaken(userTaken);

        Todo savedTodo = todoRepository.save(updateTodo);


        // RETURN UPDATED TODO:
        todoResponseBody.setTodo(savedTodo);
        todoResponseBody.addMessage("Todo with id "
                + updateTodoStatusDTO.getTodoId()
                + " was updated and notification-mail was sent to user "
                + updateTodo.getUserOffered().getUsername()
                + ".");
        return new ResponseEntity<>(todoResponseBody, HttpStatus.OK);
    }

}
