package enigma.toDoList.utils.specification;

import enigma.toDoList.model.Status;
import enigma.toDoList.model.TodoItem;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StatusSpecification {
    public static Specification<TodoItem> getSpecification(String status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (status != null && !status.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("status")),
                        "%" + status.toLowerCase() + "%")
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
