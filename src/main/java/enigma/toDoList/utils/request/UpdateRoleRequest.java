package enigma.toDoList.utils.request;

import lombok.*;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleRequest {
    private String role;
}
