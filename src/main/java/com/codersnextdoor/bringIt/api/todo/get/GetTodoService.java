package com.codersnextdoor.bringIt.api.todo.get;

import com.codersnextdoor.bringIt.api.todo.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class GetTodoService {

    static int REMAINING_DAYS_IN_DB = 3;

    @Autowired
    private TodoRepository todoRepository;

    public void deleteTodosExpiredDaysAgo() {
        LocalDateTime xDaysAgo = LocalDateTime.now().minusDays(REMAINING_DAYS_IN_DB);
        todoRepository.deleteTodosExpiredBefore(xDaysAgo);
    }

    public void updateTodoStatus() {
        todoRepository.setTodosExpiredStatus();
    }
}
