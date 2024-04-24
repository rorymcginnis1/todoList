package com.example.todo.api;

import com.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TodoApiController {

    @Autowired
    private TodoService todoService; 

    @GetMapping
    public List<Map<String, Object>> getTasks() {
        return todoService.getTasks();
    }
}
