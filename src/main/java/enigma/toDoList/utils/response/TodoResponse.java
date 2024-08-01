package enigma.toDoList.utils.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TodoResponse {
    private String id;
    private String title;
    private String description;
    private String dueDate;
    private String status;
    private String createdAt;
}
