package enigma.toDoList.utils.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateTodoRequest {
    private String title;
    private String description;
    private String dueDate;
    private String status;
}
