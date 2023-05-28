package bg.rumen.Bookstore.util;

import bg.rumen.Bookstore.models.PageResult;
import bg.rumen.Bookstore.params.PageParams;

import java.util.ArrayList;
import java.util.List;

public class PageManager {


    public static <T> PageResult<T> getPages(List<T> items, PageParams pageParams) {

        PageResult<T> pageResult = new PageResult<>();
        int totalItems = items.size();
        Integer page = pageParams.getPage();
        Integer limit = pageParams.getLimit();

        if (totalItems == 0) {
            pageResult.setItems(new ArrayList<>());
            pageResult.setTotalRecords(totalItems);
            return pageResult;
        }

        if (page != null) {

            int startIndex = Math.min(page * limit - limit, items.size() - 1);
            if (startIndex <= 0) {
                startIndex = 0;
            }
            int endIndex = Math.min(page * limit, items.size());
            if (endIndex <= 0) {
                endIndex = 1;
            }
             items = items.subList(startIndex, endIndex);
        }

        pageResult.setItems(items);
        pageResult.setTotalRecords(totalItems);

        return pageResult;
    }
}
