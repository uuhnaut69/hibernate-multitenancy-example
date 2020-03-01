package com.uuhnaut69.demo.controller;

import com.uuhnaut69.demo.model.Todo;
import com.uuhnaut69.demo.request.TodoRequest;
import com.uuhnaut69.demo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<Todo> findAll() {
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    public Todo findById(@PathVariable UUID id) throws Exception {
        return todoService.findById(id);
    }

    @PostMapping
    public Todo create(@RequestBody @Valid TodoRequest todoRequest) {
        return todoService.create(todoRequest);
    }

    @PutMapping("/{id}")
    public Todo update(@PathVariable UUID id, @RequestBody @Valid TodoRequest todoRequest) throws Exception {
        return todoService.update(id, todoRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws Exception {
        todoService.delete(id);
    }
}
