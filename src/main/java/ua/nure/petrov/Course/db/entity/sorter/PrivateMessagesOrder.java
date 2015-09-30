package ua.nure.petrov.Course.db.entity.sorter;

import ua.nure.petrov.Course.db.entity.db.PrivateMessage;

import java.util.Comparator;

/**
 * Created by Владислав on 06.08.2015.
 */
public class PrivateMessagesOrder {

    public static final Comparator<PrivateMessage> SORT_BY_POST_DATE = new Comparator<PrivateMessage>() {

        @Override
        public int compare(PrivateMessage fstPm, PrivateMessage sndPm) {
            if (fstPm.getPostDate().before(sndPm.getPostDate())) {
                return -1;
            } else {
                return 1;
            }
        }
    };

}
