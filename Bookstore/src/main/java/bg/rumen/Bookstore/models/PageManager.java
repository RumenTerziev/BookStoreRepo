package bg.rumen.Bookstore.models;

import java.util.ArrayList;
import java.util.List;

public class PageManager {

    public static <T> List<T> getPages(List<T> tList, Integer limit, Integer page) {
        if (page != null && limit != null) {

            int startIndex = Math.min(page * limit - limit, tList.size() - 1);
            if (startIndex <= 0) {
                startIndex = 0;
            }
            int endIndex = Math.min(page * limit, tList.size());
            if (endIndex <= 0) {
                endIndex = 1;
            }
             tList = tList.subList(startIndex, endIndex);
        } else {
            tList = new ArrayList<>();
        }

        return tList;
    }
}
