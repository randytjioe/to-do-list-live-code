package enigma.toDoList.repository;

import enigma.toDoList.model.Status;
import enigma.toDoList.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoItemRepository extends JpaRepository<TodoItem, Integer>, JpaSpecificationExecutor<TodoItem> {
}
