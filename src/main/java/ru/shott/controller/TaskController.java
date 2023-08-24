package ru.shott.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shott.domain.Task;
import ru.shott.exception.InvalidIdException;
import ru.shott.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String allTasks(Model model,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "limit", required = false, defaultValue = "10") int limit)
    {
        List<Task> allTasks = taskService.getAll((page - 1) * limit, limit);
        model.addAttribute("allTasks", allTasks);
        model.addAttribute("current_page", page);
        int totalPages = (int) Math.ceil(1.0 * taskService.getCount() / limit);
        if (totalPages > 1) {
            List<Integer> collect = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", collect);
        }
        return "tasks";

    }

    @PostMapping("/{id}")
    public String edit(Model model,
                       @PathVariable Integer id,
                       @RequestBody TaskInfo taskInfo){
        if(isNull(id) || id <= 0){
            throw new InvalidIdException("Invalid id!");
        }
        taskService.edit(id, taskInfo.getDescription(), taskInfo.getStatus());
        return allTasks(model, 1, 10);
    }

    @PostMapping("/")
    public String add(Model model,
                      @RequestBody TaskInfo taskInfo){
        taskService.create(taskInfo.getDescription(), taskInfo.getStatus());
        return allTasks(model, 1, 10);
    }

    @DeleteMapping("/{id}")
    public String delete(Model model,
                         @PathVariable Integer id){
        if(isNull(id) || id <= 0){
            throw new InvalidIdException("Invalid id!");
        }
        taskService.delete(id);
        return allTasks(model, 1, 10);
    }

}
