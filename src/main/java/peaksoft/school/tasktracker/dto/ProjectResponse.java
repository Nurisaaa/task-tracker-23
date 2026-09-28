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
    private String name;
    private String description;
    private int totalTasks;
    private int doneTasks;
    private List<UserProfileResponse> userProfileResponses;

    public ProjectResponse(Long id, String name, String description, int totalTasks, int doneTasks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalTasks = totalTasks;
        this.doneTasks = doneTasks;
    }
}
