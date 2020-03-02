package com.uuhnaut69.demo.service.impl;

import com.uuhnaut69.demo.exception.NotFoundException;
import com.uuhnaut69.demo.model.Todo;
import com.uuhnaut69.demo.repository.TodoRepository;
import com.uuhnaut69.demo.request.TodoRequest;
import com.uuhnaut69.demo.service.TodoService;
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

    /**
     * Save
     *
     * @param todo
     * @param todoRequest
     * @return Todo
     */
    private Todo save(Todo todo, TodoRequest todoRequest) {
        todo.setName(todoRequest.getName());
        todo.setStatus(todoRequest.getStatus());
        return todoRepository.save(todo);
    }
}
