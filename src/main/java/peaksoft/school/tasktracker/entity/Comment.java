package peaksoft.school.tasktracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "comments") // "user" - зарезервированное слово в PostgreSQL
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Task task;
    @ManyToOne
    private User author;
    private String text;
    private LocalDate creatAt;
}
