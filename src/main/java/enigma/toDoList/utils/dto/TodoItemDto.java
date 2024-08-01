package enigma.toDoList.utils.dto;

import enigma.toDoList.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemDto {

    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Description cannot be blank")
    private String description;

    private Integer userId;
    @NotNull(message = "Due date cannot be null")
    private LocalDate dueDate;
    private Status status;


}
