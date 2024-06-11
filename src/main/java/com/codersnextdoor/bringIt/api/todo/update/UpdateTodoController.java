package com.codersnextdoor.bringIt.api.todo.update;

import com.codersnextdoor.bringIt.api.todo.Todo;
import com.codersnextdoor.bringIt.api.todo.TodoRepository;
import com.codersnextdoor.bringIt.api.todo.TodoResponseBody;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/todo/")
public class UpdateTodoController {

    @Autowired
    private TodoRepository todoRepository;


    /**
     * UPDATE TODO
     * Rest Path for PUT-Request: "localhost:8081/api/todo/"
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
     * @return  - ResponseBody incl. the newly created and saved todo, a message or errorMessage and StatusCode
     *          - Everything correct: todo, success-message, StatusCode 200 (OK)
     * possible Exceptions and StatusCodes:
     * - Requestbody is empty: errorMessage, StatusCode 400 (BAD_REQUEST)
     * - Todo doesn't exist: errorMessage, StatusCode 404 (NOT_FOUND)
     * - expiredAt is before current DateTime: errorMessage, StatusCode 400 (BAD_REQUEST)
     * - Updated of Todo was not successfull: errorMessage, StatusCode 500 (INTERNAL_SERVER_ERROR)
     */
    @PutMapping("/")
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
            updateTodo.setTitle(updateTodo.getTitle());
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

        this.todoRepository.save(updateTodo);


        // CHECK IF TODO WAS SAVED IN DATABASE:
        optionalTodo = todoRepository.findByTodoIdAndLocationAndTitleAndDescriptionAndAddInfoAndExpiresAt(
                updateTodoDTO.getTodoId(),
                updateTodoDTO.getLocation(),
                updateTodoDTO.getTitle(),
                updateTodoDTO.getDescription(),
                updateTodoDTO.getAddInfo(),
                updateTodoDTO.getExpiresAt()
        );
        if (!optionalTodo.isPresent()) {
            todoResponseBody.addErrorMessage("Update of Todo with id " + updateTodoDTO.getTodoId() + " was not successfull!");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        // RETURN UPDATED TODO:
        todoResponseBody.setTodo(updateTodo);
        todoResponseBody.addMessage("Todo with id " + updateTodoDTO.getTodoId() + " was successfully updated!");
        return new ResponseEntity<>(todoResponseBody, HttpStatus.OK);
    }






}
