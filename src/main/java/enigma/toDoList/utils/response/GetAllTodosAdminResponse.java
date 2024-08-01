package enigma.toDoList.utils.response;

import enigma.toDoList.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetAllTodosAdminResponse {
    private String id;
    private String userId;
    private String title;
    private String description;
    private String dueDate;
    private String status;
    private String createdAt;
}
