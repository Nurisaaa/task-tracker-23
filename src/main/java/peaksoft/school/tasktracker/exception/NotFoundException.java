package peaksoft.school.tasktracker.exception;

/** Бросайте, когда сущность не найдена (404). */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
