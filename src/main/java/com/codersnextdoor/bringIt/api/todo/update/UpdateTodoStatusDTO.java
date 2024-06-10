package com.codersnextdoor.bringIt.api.todo.update;

public class UpdateTodoStatusDTO {

    private Long todoId;
    private Long userTakenId;
    private String status;


    public Long getUserTakenId() {
        return userTakenId;
    }

    public void setUserTakenId(Long userTakenId) {
        this.userTakenId = userTakenId;
    }

    public Long getTodoId() {
        return todoId;
    }

    public void setTodoId(Long todoId) {
        this.todoId = todoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
