package enigma.toDoList.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "todo_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
