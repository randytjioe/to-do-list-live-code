package enigma.toDoList.utils.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
@Getter
@Setter
public class PageUserResponse<T> {
    private List<T> item;
    private Integer totalItems;
    private Integer totalPages;
    private Integer currentPage;

    public PageUserResponse(Page<T> page) {
        this.item = page.getContent();
        this.totalItems = page.getSize();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();

    }
}
