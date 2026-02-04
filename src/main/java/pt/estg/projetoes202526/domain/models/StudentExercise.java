package pt.estg.projetoes202526.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "student_exercise", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "exercise_id"})
})
public class StudentExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private Integer currentPhase = 0;

    private Integer totalPhases = 0;

    private boolean completed = false;

    private Double grade;

    private boolean helpRequested = false;

    // --------------------------------- Constructors --------------------------------------
    public StudentExercise() {
    }

    public StudentExercise(User student, Exercise exercise, Integer totalPhases) {
        this.student = student;
        this.exercise = exercise;
        this.totalPhases = totalPhases;
        this.currentPhase = 0;
        this.completed = false;
        this.helpRequested = false;
    }

    // --------------------------------- Getters and Setters --------------------------------------
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(Integer currentPhase) {
        this.currentPhase = currentPhase;
    }

    public Integer getTotalPhases() {
        return totalPhases;
    }

    public void setTotalPhases(Integer totalPhases) {
        this.totalPhases = totalPhases;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public boolean isHelpRequested() {
        return helpRequested;
    }

    public void setHelpRequested(boolean helpRequested) {
        this.helpRequested = helpRequested;
    }
}