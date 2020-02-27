package com.uuhnaut69.core.service;

import com.uuhnaut69.core.model.Todo;
import com.uuhnaut69.core.payload.request.TodoRequest;

import java.util.List;
import java.util.UUID;

public interface TodoService {

    Todo findById(UUID id) throws Exception;

    List<Todo> findAll();

    Todo create(TodoRequest todoRequest);

    Todo update(UUID id, TodoRequest todoRequest) throws Exception;

    void delete(UUID id) throws Exception;

}
