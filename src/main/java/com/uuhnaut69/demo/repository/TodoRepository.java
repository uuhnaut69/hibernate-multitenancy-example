package com.uuhnaut69.demo.repository;

import com.uuhnaut69.demo.model.Todo;
import com.uuhnaut69.demo.repository.custom.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TodoRepository extends BaseRepository<Todo, UUID> {
}
