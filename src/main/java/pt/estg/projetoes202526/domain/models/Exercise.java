package pt.estg.projetoes202526.domain.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;

    @ManyToMany(mappedBy = "exercises")
    private Set<CourseUnit> courseUnits;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private Set<Question> questions = new HashSet<>();
}