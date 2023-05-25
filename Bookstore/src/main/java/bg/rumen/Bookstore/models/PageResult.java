package bg.rumen.Bookstore.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResult<T> {
    private List<T> tList;
    private Integer totalRecords;
}
