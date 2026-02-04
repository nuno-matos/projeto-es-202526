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

    // -------------------------------------------- Relationships -----------------------------------------------
    // --------------------------------- Constructors, Getters and Setters --------------------------------------
    public CourseUnit() {}

    public CourseUnit(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }
    // --------------------------------- Constructors, Getters and Setters --------------------------------------
}