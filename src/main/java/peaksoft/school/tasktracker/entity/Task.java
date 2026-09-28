package peaksoft.school.tasktracker.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks") // "user" - зарезервированное слово в PostgreSQL
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Project project;
    @ManyToOne
    private User assignee;
    private String title;
    private String description;
    private String status;
    @ManyToMany
    private List<Label> labels;
    @Enumerated
    private Priority priority;
    private LocalDateTime deadline;
    private LocalDate createAt;
    @OneToMany(mappedBy = "task")
    private List<Comment> comments;
}
