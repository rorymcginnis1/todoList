package com.example.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TodoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Map<String, Object>> getTasks() {
        return jdbcTemplate.queryForList("SELECT * FROM todos");
    }
}
