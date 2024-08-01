package enigma.toDoList.utils.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateTodoStatusRequest {
    private String status;
}
