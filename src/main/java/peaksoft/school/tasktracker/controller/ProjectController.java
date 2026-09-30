package peaksoft.school.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import peaksoft.school.tasktracker.dto.ProjectRequest;
import peaksoft.school.tasktracker.dto.ProjectResponse;
import peaksoft.school.tasktracker.entity.User;
import peaksoft.school.tasktracker.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    public List<ProjectResponse> getProjects(@AuthenticationPrincipal UserDetails userDetails){
        return projectService.getProjects(userDetails);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    @PostMapping
    public ResponseEntity<String> createProject(@RequestBody ProjectRequest projectRequest, @AuthenticationPrincipal User user){
        return projectService.createProject(projectRequest, user);
    }

}
