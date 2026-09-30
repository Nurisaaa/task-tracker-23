package peaksoft.school.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private Long totalTasks;
    private Long doneTasks;
    private List<UserProfileResponse> userProfileResponses;

    public ProjectResponse(Long id, String title, String description, Long totalTasks, Long doneTasks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.totalTasks = totalTasks;
        this.doneTasks = doneTasks;
    }
}
