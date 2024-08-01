package enigma.toDoList.utils.dto;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotBlank(message = "Username must no be blank")
    private String username;
}
