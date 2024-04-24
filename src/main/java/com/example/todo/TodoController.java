package com.example.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;

@Controller
public class TodoController{

    //for database operations
    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    //index page
    @GetMapping("/")
    String StartPage(Model model){
        //create the database table
        createTodoTableIfNotExists();
        List<Map<String, Object>> tasks = getTasks();
        //add tasks to the model
        model.addAttribute("tasks", tasks);
        return "main";
    }

    //create your table if it doesnt exist yet
    private void createTodoTableIfNotExists() {
        try {
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS todos (id INT AUTO_INCREMENT PRIMARY KEY, task VARCHAR(255) NOT NULL, line INT DEFAULT 0)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //return all tasks from the database
    private List<Map<String, Object>> getTasks() {
        return jdbcTemplate.queryForList("SELECT * FROM todos");    
    }
    
    //add a new task
    @PostMapping("/add")
    public String addTodo(@RequestParam String task) {
        jdbcTemplate.update("INSERT INTO todos(task) VALUES (?)", task);
        return "redirect:/";
    }

    //remove an existing task
    @PostMapping("/delete")
    public String deleteTodo(@RequestParam String task) {
        jdbcTemplate.update("DELETE FROM todos WHERE task = ?", task);
        return "redirect:/";
        
    }    

    //toggle the completion status of a task
    @PostMapping("/toggle")
    public String toggleTodo(@RequestParam String task) {
        jdbcTemplate.update("UPDATE todos SET line = CASE WHEN line = 0 THEN 1 ELSE 0 END WHERE task = ?", task);
        return "redirect:/";
        
    }

}