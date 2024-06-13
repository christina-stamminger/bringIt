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

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/todo")
public class GetTodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private GetTodoService getTodoService;


    /**
     * GET ALL TODOS:
     * Rest Path for GET-Request: "localhost:8081/api/todo"
     * - Method finds all Todos in the database that are not yet expired (expiredAt > current_timestamp) and
     *      with status = "Offen".
     * - Todos that are expired are updated and the status is changed to "Abgelaufen".
     * - Todos that are expired a certain number of days ago (DAYS_TODOS_KEPT_IN_DB_WHEN_EXPIRED) are deleted from the database.
     * The static value "days.todos.kept.in.db.after.expired" can be changed in application.properties (default = 7).
     *
     * @return - ResponseEntity with StatusCode 200 (OK). Response includes Set<Todo>
     */
    @GetMapping
    public ResponseEntity<Set<Todo>> getAll() {

        getTodoService.deleteTodosExpiredDaysAgo();
        getTodoService.updateTodoStatus();

        Set<Todo> allTodos = todoRepository.findTodosNotExpiredAndOpen();

        return ResponseEntity.ok(allTodos);
    }


    /**
     * GET TODO BY ID:
     * Rest Path for GET-Request: "localhost:8081/api/todo/{id}"
     * Method finds Todo with a specific id in the database.
     *
     * @param id (long) of the Todo
     * @return - ResponseBody incl. the wanted Todo, a pos. response message and StatusCode 302 (FOUND).
     * ResponseEntity with StatusCode 204 (NO_CONTENT) in case the Todo doesn't exist.
     */
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


    /**
     * GET TODO BY POSTAL_CODE OF USER_OFFERED:
     * Rest Path for GET-Request: "localhost:8081/api/todo/postcode/{postcode}"
     * - Method finds all Todos with a specific postalCode of the userOffered
     *      that are not yet expired (expiredAt > current_timestamp)
     *      with status = "Offen".
     * - Todos that are expired are updated and the status is changed to "Abgelaufen".
     *
     * @param postcode (String)
     * @return - ResponseEntity with StatusCode 200 (OK). Response includes Set<Todo>
     */
    @GetMapping("/postcode/{postcode}")
    public ResponseEntity<Set<Todo>> getTodoByPostalCode(
            @PathVariable
            String postcode) {

        getTodoService.updateTodoStatus();
        Set<Todo> todosOfPostalCode = todoRepository.findTodoByPostalCode(postcode);

        return ResponseEntity.ok(todosOfPostalCode);
    }


    /**
     * GET TODO BY CITY OF USER_OFFERED:
     * Rest Path for GET-Request: "localhost:8081/api/todo/city/{city}"
     * - Method finds all Todos in a specific city of the userOffered
     *      that are not yet expired (expiredAt > current_timestamp)
     *      with status = "Offen".
     * - Todos that are expired are updated and the status is changed to "Abgelaufen".
     *
     * @param city (String)
     * @return - ResponseEntity with StatusCode 200 (OK). Response includes Set<Todo>
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<Set<Todo>> getTodoByCity(
            @PathVariable
            String city) {

        getTodoService.updateTodoStatus();
        Set<Todo> todosInCity = todoRepository.findTodoByCity(city);

        return ResponseEntity.ok(todosInCity);
    }


    /**
     * GET TODO BY userId OFFERED:
     * Rest Path for GET-Request: "localhost:8081/api/todo/offeredByUser/{id}"
     * - Todos that are expired are updated and the status is changed to "Abgelaufen".
     * - Method finds all Todos in the database that are offered by a specific user (id),
     *   also todos that are expired.
     *
     * @param id (long) userOfferedId
     * @return ResponseEntity with StatusCode 200 (OK). Response includes Set<Todo>
     */
    @GetMapping("/offeredByUser/{id}")
    public ResponseEntity<Set<Todo>> getTodoByOfferedUserId(
            @PathVariable
            long id) {

        getTodoService.updateTodoStatus();
        Set<Todo> offeredTodos = todoRepository.findTodoByOfferedUserId(id);

        return ResponseEntity.ok(offeredTodos);

    }


    /**
     * GET TODO BY userId TAKEN:
     * Rest Path for GET-Request: "localhost:8081/api/todo/takenByUser/{id}"
     * - Todos that are expired are updated and the status is changed to "Abgelaufen".
     * - Method finds all Todos in the database that are taken by a specific user (id),
     *   also todos that are expired.
     *
     * @param id (long) userTakenId
     * @return ResponseEntity with StatusCode 200 (OK). Response includes Set<Todo>
     */
    @GetMapping("/takenByUser/{id}")
    public ResponseEntity<Set<Todo>> getTodoByTakenUserId(
            @PathVariable
            long id) {

        getTodoService.updateTodoStatus();
        Set<Todo> takenTodos = todoRepository.findTodoByTakenUserId(id);

        return ResponseEntity.ok(takenTodos);
    }

}
