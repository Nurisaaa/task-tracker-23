package peaksoft.school.tasktracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects") // "user" - зарезервированное слово в PostgreSQL
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User ownerId;
    private String title;
    private String description;
    private LocalDate creatAt;
    @ManyToMany
    private List<User> member;

    public  Project(User ownerId, String title, String description, LocalDate creatAt) {
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.creatAt = creatAt;
    }
}
