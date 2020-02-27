package com.uuhnaut69.core.service.impl;

import com.uuhnaut69.core.exception.NotFoundException;
import com.uuhnaut69.core.model.Todo;
import com.uuhnaut69.core.payload.request.TodoRequest;
import com.uuhnaut69.core.repository.TodoRepository;
import com.uuhnaut69.core.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public Todo findById(UUID id) throws Exception {
        return todoRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found !!!"));
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Todo create(TodoRequest todoRequest) {
        return save(new Todo(), todoRequest);
    }

    @Override
    public Todo update(UUID id, TodoRequest todoRequest) throws Exception {
        Todo todo = findById(id);
        return save(todo, todoRequest);
    }

    @Override
    public void delete(UUID id) throws Exception {
        Todo todo = findById(id);
        todoRepository.delete(todo);
    }

    private Todo save(Todo todo, TodoRequest todoRequest) {
        todo.setName(todoRequest.getName());
        return todoRepository.save(todo);
    }
}
