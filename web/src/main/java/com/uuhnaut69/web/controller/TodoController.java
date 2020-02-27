package com.uuhnaut69.web.controller;

import com.uuhnaut69.core.model.Todo;
import com.uuhnaut69.core.payload.request.TodoRequest;
import com.uuhnaut69.core.service.TodoService;
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
