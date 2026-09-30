package peaksoft.school.tasktracker.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import peaksoft.school.tasktracker.dto.ProjectRequest;
import peaksoft.school.tasktracker.dto.ProjectResponse;

import peaksoft.school.tasktracker.entity.Project;
import peaksoft.school.tasktracker.entity.User;
import peaksoft.school.tasktracker.repository.ProjectRepository;
import peaksoft.school.tasktracker.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<ProjectResponse> getProjects(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<ProjectResponse> projects = projectRepository.getProjects(user.getId());
        projects.forEach(p ->
                p.setUserProfileResponses(projectRepository.getMembers(p.getId())));
        return projects;
    }


    public ResponseEntity<String> createProject(ProjectRequest projectRequest, User user) {
        Project project = new Project(user, projectRequest.getTitle(), projectRequest.getDescription(), LocalDate.now());
        projectRepository.save(project);
        return ResponseEntity.ok("Project created");
    }
}
