package ua.nure.petrov.Course.db.entity.sorter;

import ua.nure.petrov.Course.db.entity.db.News;

import java.util.Comparator;

/**
 * Created by Владислав on 06.08.2015.
 */
public class NewsOrder {

    public static final Comparator<News> SORT_BY_POST_DATE = new Comparator<News>() {

        @Override
        public int compare(News fstN, News sndN) {
            if (fstN.getPostDate().after(sndN.getPostDate())) {
                return -1;
            } else {
                return 1;
            }
        }
    };

}
