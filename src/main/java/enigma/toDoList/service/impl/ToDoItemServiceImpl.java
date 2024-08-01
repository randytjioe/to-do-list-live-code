package enigma.toDoList.service.impl;

import enigma.toDoList.model.Status;
import enigma.toDoList.model.TodoItem;
import enigma.toDoList.model.User;
import enigma.toDoList.repository.ToDoItemRepository;
import enigma.toDoList.repository.UserRepository;
import enigma.toDoList.security.JwtService;
import enigma.toDoList.service.ToDoItemService;
import enigma.toDoList.service.UserService;
import enigma.toDoList.utils.dto.TodoItemDto;
import enigma.toDoList.utils.request.UpdateTodoRequest;
import enigma.toDoList.utils.request.UpdateTodoStatusRequest;
import enigma.toDoList.utils.response.GetAllTodosAdminResponse;
import enigma.toDoList.utils.response.TodoResponse;
import enigma.toDoList.utils.specification.StatusSpecification;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoItemServiceImpl implements ToDoItemService {
    @Autowired
    private final ToDoItemRepository toDoItemRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public Page<TodoItem> getAllToDoItem(Pageable pageable, String status) {
        Specification<TodoItem> spec = StatusSpecification.getSpecification(status);
        return toDoItemRepository.findAll(spec,pageable);
    }

    @Override
    public Page<GetAllTodosAdminResponse> getAllToDoItemAdmin(Pageable pageable, String status) {
        Specification<TodoItem> spec = StatusSpecification.getSpecification(status);
        Page<TodoItem> listToDoItem = toDoItemRepository.findAll(spec, pageable);

        Page<GetAllTodosAdminResponse> response = listToDoItem.map(todoItem ->
                new GetAllTodosAdminResponse(
                        todoItem.getId().toString(),
                        todoItem.getUser().getId().toString(),
                        todoItem.getTitle(),
                        todoItem.getDescription(),
                        todoItem.getDueDate().toString(),
                        todoItem.getStatus().toString(),
                        todoItem.getCreatedAt().toString()
                )
        );
        return response;
    }

    @Override
    public GetAllTodosAdminResponse getToDoItemAdminById(Integer id) {
        var toDoItemId = toDoItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo List Item not found"));

       GetAllTodosAdminResponse response =
                new GetAllTodosAdminResponse(
                        toDoItemId.getId().toString(),
                        toDoItemId.getUser().getId().toString(),
                        toDoItemId.getTitle(),
                        toDoItemId.getDescription(),
                        toDoItemId.getDueDate().toString(),
                        toDoItemId.getStatus().toString(),
                        toDoItemId.getCreatedAt().toString()
                );

        return response;
    }

    @Override
    public TodoItem getToDoItemById(Integer id) {
        return toDoItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo List Item not found"));
    }

    @Override
    public TodoResponse createToDoItem(TodoItemDto req) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid or missing Authorization header");
        }

        String jwt = authHeader.substring(7);
        var userEmail = jwtService.extractClaim(jwt, Claims::getSubject);
        User userDetails = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        var todoItem =  TodoItem.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .dueDate(req.getDueDate())
                .status(Status.IN_PROGRESS)
                .createdAt(LocalDateTime.now())
                .user(userDetails)
                .build();

        TodoItem todoItemDto = toDoItemRepository.save(todoItem);
        TodoResponse response = new TodoResponse(
                todoItemDto.getId().toString(),
                todoItemDto.getTitle().toString(),
                todoItemDto.getDescription().toString(),
                todoItemDto.getDueDate().toString(),
                todoItemDto.getStatus().toString(),
                todoItemDto.getCreatedAt().toString()
        );
        return response;
    }


    @Override
    public TodoItem updateToDoItem(Integer id, UpdateTodoRequest req) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dueDate = LocalDate.parse(req.getDueDate(), formatter);
        TodoItem todoItem = this.getToDoItemById(id);
        todoItem.setTitle(req.getTitle());
        todoItem.setDescription(req.getDescription());
        todoItem.setStatus(Status.valueOf(req.getStatus()));
        todoItem.setDueDate(dueDate);

        return toDoItemRepository.save(todoItem);
    }
    @Override
    public TodoItem updateToDoItemStatus(Integer id, UpdateTodoStatusRequest req) {
        TodoItem todoItem = this.getToDoItemById(id);
        todoItem.setStatus(Status.valueOf(req.getStatus()));
        return toDoItemRepository.save(todoItem);
    }



    @Override
    public TodoItem deleteToDoItem(Integer id) {
        toDoItemRepository.deleteById(id);
        return null;
    }
}
