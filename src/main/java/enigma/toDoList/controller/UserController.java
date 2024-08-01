package enigma.toDoList.controller;
import enigma.toDoList.model.TodoItem;
import enigma.toDoList.model.User;
import enigma.toDoList.service.AuthService;
import enigma.toDoList.service.ToDoItemService;
import enigma.toDoList.service.UserService;
import enigma.toDoList.utils.dto.UserDto;
import enigma.toDoList.utils.request.CreateSuperAdminRequest;
import enigma.toDoList.utils.request.UpdateRoleRequest;
import enigma.toDoList.utils.response.GetAllTodosAdminResponse;
import enigma.toDoList.utils.response.PageResponse;
import enigma.toDoList.utils.response.PageUserResponse;
import enigma.toDoList.utils.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ToDoItemService toDoItemService;

    @GetMapping("/users")
    public PageUserResponse<?> getAll(
            @PageableDefault Pageable pageable
    ) {
        return new PageUserResponse<>(userService.getAll(pageable));
    }

    @GetMapping("/users/{id}")
    public User getById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @PatchMapping("/users/{id}")
    public User changeUserRole( @RequestHeader("X-Admin-Secret-Key") String secretKey,@PathVariable Integer id, @RequestBody UpdateRoleRequest role) {
        return userService.changeUserRole(id, role,secretKey);
    }

    @GetMapping("/todos")
    public PageResponse<GetAllTodosAdminResponse> getAllToDoItem(@PageableDefault Pageable pageable,
                                                 @RequestParam(required = false) String status) {
        return new PageResponse<GetAllTodosAdminResponse>(toDoItemService.getAllToDoItemAdmin(pageable,status));
    }

    @GetMapping("/todos/{id}")
    public PageResponse<GetAllTodosAdminResponse> getAllToDoItemId(@PageableDefault Pageable pageable,
                                                                 @RequestParam(required = false) String status) {
        return new PageResponse<GetAllTodosAdminResponse>(toDoItemService.getAllToDoItemAdmin(pageable,status));
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
    }

    @PostMapping("/super-admin")
    public User createSuperAdmin(
            @RequestHeader("X-Super-Admin-Secret-Key") String secretKey,
            @RequestBody CreateSuperAdminRequest request) {

        return   userService.createSuperAdmin(request, secretKey);
    }


}


