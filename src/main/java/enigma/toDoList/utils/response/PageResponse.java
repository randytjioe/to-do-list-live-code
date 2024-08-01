package enigma.toDoList.utils.response;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class PageResponse<T> {
    private List<T> item;
    private Integer totalItems;
    private Integer totalPages;
    private Integer currentPage;

    public PageResponse(Page<T> page) {
        this.item = page.getContent();
        this.totalItems = page.getSize();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();

    }
}
