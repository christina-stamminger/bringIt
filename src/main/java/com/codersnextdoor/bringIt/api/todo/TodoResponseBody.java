package com.codersnextdoor.bringIt.api.todo;

import com.codersnextdoor.bringIt.api.ResponseBody;

public class TodoResponseBody extends ResponseBody {

    private Todo todo;

    public TodoResponseBody() {

    }

    public TodoResponseBody(Todo todo) {
        this.todo = todo;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }
}
