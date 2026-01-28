package pt.estg.projetoes202526.domain.models;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class CourseUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    // -------------------------------------------- Relationships -----------------------------------------------
    @ManyToMany
    @JoinTable(
            name = "course_unit_user",
            joinColumns = @JoinColumn(name = "course_unit_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<User> users = new HashSet<User>();

    @ManyToMany
    @JoinTable(
            name = "course_unit_exercise",
            joinColumns = @JoinColumn(name = "course_unit_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    )
    private Set<Exercise> exercises;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    // -------------------------------------------- Relationships -----------------------------------------------
    // --------------------------------- Constructors, Getters and Setters --------------------------------------
    // --------------------------------- Constructors, Getters and Setters --------------------------------------
}