package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@GetMapping
	public String index(Model model) {
		model.addAttribute("todos", todoService.getAllTodos());
		model.addAttribute("todo", new Todo());
		return "index";
	}

	@PostMapping("/add")
	public String addTodo(@ModelAttribute Todo todo) {
		if (todo.getTitle() != null && !todo.getTitle().isEmpty()) {
			todoService.createTodo(todo);
		}
		return "redirect:/";
	}

	@PostMapping("/toggle/{id}")
	public String toggleTodo(@PathVariable Long id) {
		todoService.toggleTodo(id);
		return "redirect:/";
	}

	@PostMapping("/delete/{id}")
	public String deleteTodo(@PathVariable Long id) {
		todoService.deleteTodo(id);
		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String editTodoPage(@PathVariable Long id, Model model) {
		var todo = todoService.getTodoById(id);
		if (todo.isPresent()) {
			model.addAttribute("todo", todo.get());
			model.addAttribute("todos", todoService.getAllTodos());
			return "edit";
		}
		return "redirect:/";
	}

	@PostMapping("/update/{id}")
	public String updateTodo(@PathVariable Long id, @ModelAttribute Todo todo) {
		todoService.updateTodo(id, todo);
		return "redirect:/";
	}
}
