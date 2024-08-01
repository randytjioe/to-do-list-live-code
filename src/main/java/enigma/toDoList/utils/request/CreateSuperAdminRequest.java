package enigma.toDoList.utils.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSuperAdminRequest {
    private String username;
    private String email;
    private String password;
}
