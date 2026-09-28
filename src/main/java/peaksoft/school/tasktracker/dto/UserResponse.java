package peaksoft.school.tasktracker.dto;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        String role,
        LocalDateTime createdAt
) {
}
