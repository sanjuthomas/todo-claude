package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

	@Mock
	private TodoRepository todoRepository;

	@InjectMocks
	private TodoService todoService;

	private Todo todo1;
	private Todo todo2;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		todo1 = new Todo("Learn Spring Boot", "Complete Spring Boot tutorial");
		todo1.setId(1L);
		todo1.setCompleted(false);

		todo2 = new Todo("Build TODO App", "Create a simple TODO application");
		todo2.setId(2L);
		todo2.setCompleted(false);
	}

	@Test
	void testGetAllTodos() {
		List<Todo> todos = Arrays.asList(todo1, todo2);
		when(todoRepository.findAll()).thenReturn(todos);

		List<Todo> result = todoService.getAllTodos();

		assertEquals(2, result.size());
		assertEquals("Learn Spring Boot", result.get(0).getTitle());
		verify(todoRepository, times(1)).findAll();
	}

	@Test
	void testGetTodoById() {
		when(todoRepository.findById(1L)).thenReturn(Optional.of(todo1));

		Optional<Todo> result = todoService.getTodoById(1L);

		assertTrue(result.isPresent());
		assertEquals("Learn Spring Boot", result.get().getTitle());
		verify(todoRepository, times(1)).findById(1L);
	}

	@Test
	void testGetTodoByIdNotFound() {
		when(todoRepository.findById(999L)).thenReturn(Optional.empty());

		Optional<Todo> result = todoService.getTodoById(999L);

		assertFalse(result.isPresent());
		verify(todoRepository, times(1)).findById(999L);
	}

	@Test
	void testCreateTodo() {
		Todo newTodo = new Todo("Test TODO", "This is a test");
		when(todoRepository.save(newTodo)).thenReturn(newTodo);

		Todo result = todoService.createTodo(newTodo);

		assertNotNull(result);
		assertEquals("Test TODO", result.getTitle());
		assertFalse(result.isCompleted());
		verify(todoRepository, times(1)).save(newTodo);
	}

	@Test
	void testUpdateTodo() {
		Todo updatedTodo = new Todo("Updated Title", "Updated Description");
		updatedTodo.setCompleted(true);

		when(todoRepository.findById(1L)).thenReturn(Optional.of(todo1));
		when(todoRepository.save(any(Todo.class))).thenReturn(updatedTodo);

		Todo result = todoService.updateTodo(1L, updatedTodo);

		assertNotNull(result);
		assertEquals("Updated Title", result.getTitle());
		assertTrue(result.isCompleted());
		verify(todoRepository, times(1)).findById(1L);
		verify(todoRepository, times(1)).save(any(Todo.class));
	}

	@Test
	void testDeleteTodo() {
		todoService.deleteTodo(1L);
		verify(todoRepository, times(1)).deleteById(1L);
	}

	@Test
	void testToggleTodo() {
		when(todoRepository.findById(1L)).thenReturn(Optional.of(todo1));
		when(todoRepository.save(any(Todo.class))).thenReturn(todo1);

		todoService.toggleTodo(1L);

		verify(todoRepository, times(1)).findById(1L);
		verify(todoRepository, times(1)).save(any(Todo.class));
	}

	@Test
	void testToggleTodoNotFound() {
		when(todoRepository.findById(999L)).thenReturn(Optional.empty());

		todoService.toggleTodo(999L);

		verify(todoRepository, times(1)).findById(999L);
		verify(todoRepository, times(0)).save(any(Todo.class));
	}
}
