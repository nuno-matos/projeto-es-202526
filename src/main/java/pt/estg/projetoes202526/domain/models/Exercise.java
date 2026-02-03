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

    public Exercise() {}
    public Exercise(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CourseUnit> getCourseUnits() {
        return courseUnits;
    }

    public void setCourseUnits(Set<CourseUnit> courseUnits) {
        this.courseUnits = courseUnits;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}