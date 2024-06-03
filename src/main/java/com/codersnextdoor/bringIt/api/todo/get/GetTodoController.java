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


    // GET ALL TODOS:
    @GetMapping("/")
    public ResponseEntity<List<Todo>> getAll() {
        List<Todo> allTodos = todoRepository.findAll();
        System.out.println("All TODOS:");
        System.out.println(allTodos);

        // return ResponseEntity.ok("Hallo");
        return ResponseEntity.ok(allTodos);
    }


    // GET TODO BY ID:

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(
            @PathVariable
            long id) {
        Optional<Todo> optionalTodo = this.todoRepository.findById(id);

        if (!optionalTodo.isPresent()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        Todo todo = optionalTodo.get();

        return ResponseEntity.ok(todo);


    }


}
