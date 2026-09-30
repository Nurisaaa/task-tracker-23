package peaksoft.school.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.school.tasktracker.dto.ProjectResponse;
import peaksoft.school.tasktracker.dto.UserProfileResponse;
import peaksoft.school.tasktracker.entity.Project;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("""
    select new peaksoft.school.tasktracker.dto.ProjectResponse(
        p.id,
        p.title,
        p.description,
        (select count(t) from Task t where t.project = p),
        (select count(t) from Task t where t.project = p and t.status = 'DONE')
    )
    from Project p
    where p.ownerId.id = :userId
       or exists (select m.id from p.member m where m.id = :userId)
    """)
    List<ProjectResponse> getProjects(@Param("userId") Long userId);

    @Query("""
        select new peaksoft.school.tasktracker.dto.UserProfileResponse(m.id, m.fullName)
        from Project p
        join p.member m
        where p.id = :projectId
        """)
    List<UserProfileResponse> getMembers(@Param("projectId") Long projectId);
}
