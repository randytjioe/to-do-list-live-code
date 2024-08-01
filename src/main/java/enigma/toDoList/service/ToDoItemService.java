package enigma.toDoList.service;

import enigma.toDoList.model.Status;
import enigma.toDoList.model.TodoItem;
import enigma.toDoList.utils.dto.TodoItemDto;
import enigma.toDoList.utils.request.UpdateTodoRequest;
import enigma.toDoList.utils.request.UpdateTodoStatusRequest;
import enigma.toDoList.utils.response.GetAllTodosAdminResponse;
import enigma.toDoList.utils.response.TodoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ToDoItemService {
    Page<TodoItem> getAllToDoItem(Pageable pageable, String status);
    Page<GetAllTodosAdminResponse> getAllToDoItemAdmin(Pageable pageable, String status);
    TodoItem getToDoItemById(Integer id);
    TodoResponse createToDoItem(TodoItemDto todoItemDto);
    TodoItem updateToDoItem(Integer id, UpdateTodoRequest updateTodoRequest);
    TodoItem deleteToDoItem(Integer id);
    TodoItem updateToDoItemStatus(Integer id, UpdateTodoStatusRequest status);

    GetAllTodosAdminResponse getToDoItemAdminById(Integer id);
}
