package com.codersnextdoor.bringIt.api.todo.delete;

import com.codersnextdoor.bringIt.api.ResponseBody;
import com.codersnextdoor.bringIt.api.todo.Todo;
import com.codersnextdoor.bringIt.api.todo.TodoRepository;
import com.codersnextdoor.bringIt.api.todo.TodoResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/todo/")

public class DeleteTodoController {

    @Autowired
    private TodoRepository todoRepository;

    /**
     * DELETE TODO BY ID:
     * Rest Path for DELETE-Request: "localhost:8081/api/todo/{id}"
     * Method finds Todo with a specific id in the database, checks if it exists and if so removes it from database.
     *
     * @param id (long) of the Todo
     * @return  - ResponseBody incl. confirmation message, StatusCode 200 (OK)
     *          - ResponseBody incl. errorMessage if todo doesn't exist, StatusCode 404 (NOT_FOUND)
     *          - ResponseBody incl. errorMessage if todo exists but couldn't be deleted, StatusCode 503 (SERVICE_UNAVAILABLE)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteTodoById (
            @PathVariable
            long id) {

        Optional<Todo> optionalTodo = todoRepository.findById(id);

        TodoResponseBody todoResponseBody = new TodoResponseBody();
        if (!optionalTodo.isPresent()) {
            todoResponseBody.addErrorMessage("Todo with id " + id + "does not exist");
            return new ResponseEntity(todoResponseBody, HttpStatus.NOT_FOUND);
        }

        todoRepository .deleteById(id);

        optionalTodo = todoRepository.findById(id);

        if (optionalTodo.isPresent()) {
            todoResponseBody.addErrorMessage("Could not delete todo with id " + id + ".");
            return new ResponseEntity(todoResponseBody, HttpStatus.SERVICE_UNAVAILABLE);
        } else {
            todoResponseBody.addMessage("Todo " + id + " has been deleted");
            return new ResponseEntity(todoResponseBody, HttpStatus.OK);
        }

    }

}
