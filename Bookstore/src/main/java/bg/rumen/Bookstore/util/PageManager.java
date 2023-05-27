package bg.rumen.Bookstore.util;

import bg.rumen.Bookstore.params.PageParams;

import java.util.List;

public class PageManager {

    public static <T> List<T> getPages(List<T> tList, PageParams pageParams) {
        Integer page = pageParams.getPage();
        Integer limit = pageParams.getLimit();

        if (page != null) {

            int startIndex = Math.min(page * limit - limit, tList.size() - 1);
            if (startIndex <= 0) {
                startIndex = 0;
            }
            int endIndex = Math.min(page * limit, tList.size());
            if (endIndex <= 0) {
                endIndex = 1;
            }
             tList = tList.subList(startIndex, endIndex);
        }

        return tList;
    }
}
