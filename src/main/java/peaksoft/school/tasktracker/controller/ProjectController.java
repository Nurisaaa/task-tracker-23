package peaksoft.school.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.school.tasktracker.dto.ProjectResponse;
import peaksoft.school.tasktracker.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public List<ProjectResponse> getProjects(@AuthenticationPrincipal UserDetails userDetails){
        return projectService.getProjects(userDetails);
    }

}
