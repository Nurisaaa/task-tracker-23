package peaksoft.school.tasktracker.exception;

/** Бросайте, когда пользователь авторизован, но не имеет прав на действие (403). */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
