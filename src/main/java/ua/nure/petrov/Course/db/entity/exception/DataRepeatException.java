package ua.nure.petrov.Course.db.entity.exception;

/**
 * Created by Владислав on 10.08.2015.
 */
public class DataRepeatException extends RuntimeException {

    public DataRepeatException() {
    }

    public DataRepeatException(String message) {
        super(message);
    }

    public DataRepeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataRepeatException(Throwable cause) {
        super(cause);
    }

    public DataRepeatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
