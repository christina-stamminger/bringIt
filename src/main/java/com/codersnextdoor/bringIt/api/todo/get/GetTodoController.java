package com.codersnextdoor.bringIt.api.todo.get;

import com.codersnextdoor.bringIt.api.todo.Todo;
import com.codersnextdoor.bringIt.api.todo.TodoRepository;
import com.codersnextdoor.bringIt.api.todo.TodoResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/todo/")
public class GetTodoController {

    @Autowired
    private TodoRepository todoRepository;


    // GET ALL TODOS (Admin Function):
    @GetMapping("/")
    public ResponseEntity<List<Todo>> getAll() {
        List<Todo> allTodos = todoRepository.findAll();
        return ResponseEntity.ok(allTodos);
    }

    // GET ALL TODOS (not yet expired, if expired than delete this Todo)



    // GET TODO BY ID:

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseBody> getTodoById(
            @PathVariable
            long id) {
        Optional<Todo> optionalTodo = this.todoRepository.findById(id);

        if (!optionalTodo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Todo todo = optionalTodo.get();

        TodoResponseBody todoResponseBody = new TodoResponseBody();
        todoResponseBody.addMessage("Todo with id " + id + " exists.");
        todoResponseBody.setTodo(todo);
        return new ResponseEntity<>(todoResponseBody, HttpStatus.FOUND);

    }

    // GET TODO BY postalCode OF UserOffered

//    @GetMapping("/postalCode/{postalCode}")
//    public ResponseEntity<TodoResponseBody> getTodoByPostalCode(
//            @PathVariable
//            String postalCode) {
//
//    }



    // GET TODO BY city OF UserOffered


    // GET TODO BY userId OFFERED:
    @GetMapping("/offerdByUser/{id}")
    public ResponseEntity<Set<Todo>> getTodoByOfferedUserId(
            @PathVariable
            long id) {

        Set<Todo> offeredTodos = todoRepository.findTodoByOfferedUserId(id);

        return ResponseEntity.ok(offeredTodos);

    }


    // GET TODO BY userId TAKEN:
    @GetMapping("/takenByUser/{id}")
    public ResponseEntity<Set<Todo>> getTodoByTakenUserId(
            @PathVariable
            long id) {

        Set<Todo> takenTodos = todoRepository.findTodoByTakenUserId(id);

        return ResponseEntity.ok(takenTodos);
    }



}

