package bg.rumen.Bookstore.params;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageParams {
    private static final Integer LIMIT = 5;
    @Positive
    private Integer page;


    public Integer getLimit() {
        return LIMIT;
    }

}
