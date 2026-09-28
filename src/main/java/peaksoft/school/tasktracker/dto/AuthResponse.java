package peaksoft.school.tasktracker.dto;

public record AuthResponse(
        String token,
        String tokenType,
        String email,
        String fullName,
        String role
) {
}
