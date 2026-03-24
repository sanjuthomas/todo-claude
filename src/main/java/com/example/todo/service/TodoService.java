package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepository;

	public List<Todo> getAllTodos() {
		return todoRepository.findAll();
	}

	public Optional<Todo> getTodoById(Long id) {
		return todoRepository.findById(id);
	}

	public Todo createTodo(Todo todo) {
		return todoRepository.save(todo);
	}

	public Todo updateTodo(Long id, Todo todoDetails) {
		Optional<Todo> optionalTodo = todoRepository.findById(id);
		if (optionalTodo.isPresent()) {
			Todo todo = optionalTodo.get();
			if (todoDetails.getTitle() != null) {
				todo.setTitle(todoDetails.getTitle());
			}
			if (todoDetails.getDescription() != null) {
				todo.setDescription(todoDetails.getDescription());
			}
			todo.setCompleted(todoDetails.isCompleted());
			return todoRepository.save(todo);
		}
		return null;
	}

	public void deleteTodo(Long id) {
		todoRepository.deleteById(id);
	}

	public void toggleTodo(Long id) {
		Optional<Todo> optionalTodo = todoRepository.findById(id);
		if (optionalTodo.isPresent()) {
			Todo todo = optionalTodo.get();
			todo.setCompleted(!todo.isCompleted());
			todoRepository.save(todo);
		}
	}
}
