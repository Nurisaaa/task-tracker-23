package peaksoft.school.tasktracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "labels") // "user" - зарезервированное слово в PostgreSQL
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
}
