package com.codersnextdoor.bringIt.api.todo.create;

import com.codersnextdoor.bringIt.api.user.User;

import java.time.LocalDateTime;

public class CreateTodoDTO {
    private long userOfferedId;
    private String title;
    private String location;
    private String description;
    private String addInfo;
    private String uploadPath;
    private LocalDateTime expiresAt;

    public long getUserOfferedId() {
        return userOfferedId;
    }

    public void setUserOfferedId(long userOfferedId) {
        this.userOfferedId = userOfferedId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
