package ua.nure.petrov.Course.db.entity.sorter;

import ua.nure.petrov.Course.db.entity.constants.Util;
import ua.nure.petrov.Course.db.entity.db.Course;

import java.util.Comparator;

/**
 * Created by Владислав on 06.08.2015.
 */
public class CourseOrder {

    public static final Comparator<Course> SORT_BY_START_DATE = new Comparator<Course>() {

        @Override
        public int compare(Course fstC, Course sndC) {
            if (fstC.getStartDate().after(sndC.getStartDate())) {
                return 1;
            } else {
                return -1;
            }
        }
    };

    public static final Comparator<Course> SORT_BY_NEAREST_DATE = new Comparator<Course>() {

        @Override
        public int compare(Course fstC, Course sndC) {
            if (fstC.getStartDate().after(sndC.getStartDate()) && fstC.getStartDate().after(Util.getYesterdaysDate())) {
                return -1;
            } else {
                return 1;
            }
        }
    };

    public static final Comparator<Course> SORT_BY_USER_COUNT = new Comparator<Course>() {

        @Override
        public int compare(Course fstC, Course sndC) {
            if (fstC.getUsersCount() > sndC.getUsersCount()) {
                return -1;
            } else {
                return 1;
            }
        }
    };

    public static final Comparator<Course> SORT_BY_NAME = new Comparator<Course>() {

        @Override
        public int compare(Course fstC, Course sndC) {
            return fstC.getName().compareTo(sndC.getName());
        }
    };

    public static final Comparator<Course> SORT_BY_DURATION = new Comparator<Course>() {

        @Override
        public int compare(Course fstC, Course sndC) {
            if (fstC.getDuration() > sndC.getDuration()) {
                return -1;
            } else {
                return 1;
            }
        }
    };

}
