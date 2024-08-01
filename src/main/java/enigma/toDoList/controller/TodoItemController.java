package enigma.toDoList.controller;

import enigma.toDoList.model.Status;
import enigma.toDoList.model.TodoItem;
import enigma.toDoList.service.ToDoItemService;
import enigma.toDoList.utils.dto.TodoItemDto;
import enigma.toDoList.utils.request.UpdateTodoRequest;
import enigma.toDoList.utils.request.UpdateTodoStatusRequest;
import enigma.toDoList.utils.response.PageResponse;
import enigma.toDoList.utils.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoItemController {
    private final ToDoItemService toDoItemService;

    @PostMapping
    @Validated
    public ResponseEntity<?> create(@Valid @RequestBody TodoItemDto newTask) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDoItemService.createToDoItem(newTask));
    }

    @GetMapping
    public PageResponse<?> getAll(@PageableDefault Pageable pageable,
    @RequestParam(required = false) String status) {
        return new PageResponse<>(toDoItemService.getAllToDoItem(pageable,status));
    }

    @GetMapping("/{id}")
    public TodoItem getById(@PathVariable Integer id) {
        return toDoItemService.getToDoItemById(id);
    }

    @PutMapping("/{id}")
    public TodoItem updateById(@PathVariable Integer id, @RequestBody UpdateTodoRequest updateTodoRequest) {
        return toDoItemService.updateToDoItem(id, updateTodoRequest);
    }

    @PatchMapping("/{id}/status")
    public TodoItem patchById(@PathVariable Integer id, @RequestBody UpdateTodoStatusRequest status) {
        return toDoItemService.updateToDoItemStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(toDoItemService.deleteToDoItem(id));

    }
}
