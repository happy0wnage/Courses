package ua.nure.petrov.Course.db.entity.exception;

/**
 * Created by Владислав on 30.07.2015.
 */
public class DBLayerException extends RuntimeException {

    public DBLayerException() {
    }

    public DBLayerException(String message) {
        super(message);
    }

    public DBLayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBLayerException(Throwable cause) {
        super(cause);
    }
}
