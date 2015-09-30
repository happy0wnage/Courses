package ua.nure.petrov.Course.db.entity.exception;

/**
 * @user Vladyslav
 * @date 02.08.2015
 * @time 17:47
 */
public class ApplicationInitializationException extends RuntimeException {
    public ApplicationInitializationException() {
    }

    public ApplicationInitializationException(String message) {
        super(message);
    }

    public ApplicationInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationInitializationException(Throwable cause) {
        super(cause);
    }
}
