package com.codersnextdoor.bringIt.api.todo.get;

import com.codersnextdoor.bringIt.api.todo.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GetTodoService {

    static int KEEPING_DAYS_IN_DB = 3;

    @Autowired
    private TodoRepository todoRepository;

    public void deleteTodosExpiredDaysAgo() {
        LocalDateTime xDaysAgo = LocalDateTime.now().minusDays(KEEPING_DAYS_IN_DB);
        todoRepository.deleteTodosExpiredBefore(xDaysAgo);
    }

    public void updateTodoStatus() {
        todoRepository.setTodosExpiredStatus();
    }
}
