package enigma.toDoList.utils.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RegisterResponse {
        private String id;
        private String username;
        private String email;
}
