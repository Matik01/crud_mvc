package ru.shott.controller;

import ru.shott.domain.Status;

public class TaskInfo {
    private String description;
    private Status status;

    public String getDescription() {
        return description;
    }

    public TaskInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public TaskInfo setStatus(Status status) {
        this.status = status;
        return this;
    }
}
