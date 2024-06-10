package com.codersnextdoor.bringIt.api.todo.update;

import com.codersnextdoor.bringIt.api.todo.Todo;
import com.codersnextdoor.bringIt.api.todo.TodoRepository;
import com.codersnextdoor.bringIt.api.todo.TodoResponseBody;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        // CHECK IF EXPIRED_AT IS VALID:
        if (!(updateTodoDTO.getExpiresAt() == null) && updateTodoDTO.getExpiresAt().isBefore(LocalDateTime.now())) {
            todoResponseBody.addErrorMessage("ExpiredAt is before current DateTime! The instant of time must be in future.");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.BAD_REQUEST);
        }



    }
}
