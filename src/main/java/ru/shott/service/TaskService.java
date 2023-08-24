package ru.shott.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.shott.domain.Status;
import ru.shott.domain.Task;
import ru.shott.repository.TaskRepository;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAll(int offset, int limit) {
        return repository.getAll(offset, limit);
    }

    public int getCount() {
        return repository.getCount();
    }

    public void edit(int id, String description, Status status) {
        Task task = repository.getById(id);
        if (!isNull(task)) {
            task.setDescription(description);
            task.setStatus(status);
            repository.saveOrUpdate(task);
        } else {
            throw new RuntimeException("Entity not found");
        }
    }

    public void create(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        repository.saveOrUpdate(task);
    }

    public void delete(int id) {
        Task task = repository.getById(id);
        if (!isNull(task)) {
            repository.delete(task);
        } else {
            throw new RuntimeException("Entity not found");
        }

    }
}
