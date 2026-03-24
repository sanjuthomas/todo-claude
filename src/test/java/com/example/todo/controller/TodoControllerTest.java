package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TodoService todoService;

	@BeforeEach
	void setUp() {
		// Clear all todos before each test
		todoService.getAllTodos().forEach(todo -> todoService.deleteTodo(todo.getId()));
	}

	@Test
	void testGetIndexPage() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(model().attributeExists("todos", "todo"));
	}

	@Test
	void testAddTodo() throws Exception {
		mockMvc.perform(post("/add")
				.param("title", "New TODO")
				.param("description", "Test Description"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));

		List<Todo> todos = todoService.getAllTodos();
		List<Todo> newTodos = todos.stream()
				.filter(t -> "New TODO".equals(t.getTitle()))
				.toList();
		org.junit.jupiter.api.Assertions.assertEquals(1, newTodos.size());
	}

	@Test
	void testAddTodoWithEmptyTitle() throws Exception {
		int sizeBefore = todoService.getAllTodos().size();

		mockMvc.perform(post("/add")
				.param("title", "")
				.param("description", "Test Description"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));

		int sizeAfter = todoService.getAllTodos().size();
		org.junit.jupiter.api.Assertions.assertEquals(sizeBefore, sizeAfter);
	}

	@Test
	void testToggleTodo() throws Exception {
		Todo todo = todoService.createTodo(new Todo("Toggle Test", "Test"));
		
		mockMvc.perform(post("/toggle/" + todo.getId()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));

		Todo updated = todoService.getTodoById(todo.getId()).get();
		org.junit.jupiter.api.Assertions.assertTrue(updated.isCompleted());
	}

	@Test
	void testDeleteTodo() throws Exception {
		Todo todo = todoService.createTodo(new Todo("Delete Test", "Test"));
		
		mockMvc.perform(post("/delete/" + todo.getId()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));

		org.junit.jupiter.api.Assertions.assertTrue(todoService.getTodoById(todo.getId()).isEmpty());
	}

	@Test
	void testEditTodoPage() throws Exception {
		Todo todo = todoService.createTodo(new Todo("Edit Test", "Test"));
		
		mockMvc.perform(get("/edit/" + todo.getId()))
				.andExpect(status().isOk())
				.andExpect(view().name("edit"))
				.andExpect(model().attributeExists("todo", "todos"));
	}

	@Test
	void testEditTodoPageNotFound() throws Exception {
		mockMvc.perform(get("/edit/999"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}

	@Test
	void testUpdateTodo() throws Exception {
		Todo todo = todoService.createTodo(new Todo("Original", "Description"));
		
		mockMvc.perform(post("/update/" + todo.getId())
				.param("title", "Updated TODO")
				.param("description", "Updated Description")
				.param("completed", "true"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));

		Todo updated = todoService.getTodoById(todo.getId()).get();
		org.junit.jupiter.api.Assertions.assertEquals("Updated TODO", updated.getTitle());
		org.junit.jupiter.api.Assertions.assertTrue(updated.isCompleted());
	}
}
