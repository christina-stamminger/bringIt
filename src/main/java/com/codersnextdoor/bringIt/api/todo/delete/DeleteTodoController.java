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
@RequestMapping("/api/todo")

public class DeleteTodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private DeleteTodoService deleteTodoService;

    /**
     * DELETE TODO BY ID:
     * Rest Path for DELETE-Request: "localhost:8081/api/todo/{id}"
     * Method finds Todo with a specific id in the database, checks if it exists and if so removes it from database
     * and generates a Notification-Mail to the userTaken of this todo if the status is "In Arbeit".
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
        if (optionalTodo.isEmpty()) {
            todoResponseBody.addErrorMessage("Todo with id " + id + " does not exist");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.NOT_FOUND);
        }


        // CASE TODO-STATUS = "In Arbeit": SEND EMAIL NOTIFICATION TO USER_TAKEN:
        Todo deleteTodo = optionalTodo.get();
        if (deleteTodo.getStatus().equals("In Arbeit")) {
            deleteTodoService.createDeleteNotificationMail(deleteTodo);
            todoResponseBody.addMessage("A notification-mail was sent to user "
                    + deleteTodo.getUserOffered().getUsername()
                    + ".");
        }


        // DELETE TODO:
        todoRepository .deleteById(id);


        // VERIFY IF TODO HAS BEEN DELETED:
        optionalTodo = todoRepository.findById(id);

        if (optionalTodo.isPresent()) {
            todoResponseBody.addErrorMessage("Could not delete todo with id " + id + ".");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.SERVICE_UNAVAILABLE);
        } else {
            todoResponseBody.addMessage("Todo " + id + " has been deleted");
            return new ResponseEntity<>(todoResponseBody, HttpStatus.OK);
        }

    }

}
