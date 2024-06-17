package com.codersnextdoor.bringIt.api.todo.get;

import com.codersnextdoor.bringIt.api.todo.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Service
public class GetTodoService {


    @Value("${days.todos.kept.in.db.after.expired}")
    private Integer DAYS_TODOS_KEPT_IN_DB_WHEN_EXPIRED;

    @Autowired
    private TodoRepository todoRepository;

    public void deleteTodosExpiredDaysAgo() {
        LocalDateTime xDaysAgo = LocalDateTime.now().minusDays(DAYS_TODOS_KEPT_IN_DB_WHEN_EXPIRED);
        todoRepository.deleteTodosExpiredBefore(xDaysAgo);
    }

    public void updateTodoStatus() {
        todoRepository.setTodosExpiredStatus();
    }
}
