//package com.example.ems.controller;
//
//import com.example.ems.entity.Task;
//import com.example.ems.repository.TaskRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/tasks")
//public class TaskController {
//
//    @Autowired
//    TaskRepository repo;
//
//    @PostMapping
//    public Task create(@RequestBody Task t){
//        return repo.save(t);
//    }
//
//    @GetMapping
//    public List<Task> all(){
//        return repo.findAll();
//    }
//}
//
