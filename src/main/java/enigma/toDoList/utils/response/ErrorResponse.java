package enigma.toDoList.utils.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ErrorResponse {

    private String message;
    private String status;
    private String error;

    public ErrorResponse(String message, String status, String error) {
        this.message = message;
        this.status = status;
        this.error = error;
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

}
