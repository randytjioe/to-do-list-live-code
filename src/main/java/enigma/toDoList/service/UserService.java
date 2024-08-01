package enigma.toDoList.service;

import enigma.toDoList.model.User;
import enigma.toDoList.utils.dto.UserDto;
import enigma.toDoList.utils.request.CreateSuperAdminRequest;
import enigma.toDoList.utils.request.UpdateRoleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {


    Page<User> getAll(Pageable pageable);

    User getById(Integer id);

    User changeUserRole(Integer id, UpdateRoleRequest updatedUser, String secretKey);

    void deleteById(Integer id);
    User createSuperAdmin(CreateSuperAdminRequest request, String secretKey);
}
