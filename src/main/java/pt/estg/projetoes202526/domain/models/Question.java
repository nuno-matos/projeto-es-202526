package pt.estg.projetoes202526.domain.models;

import jakarta.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String questionText;
    private String questionGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    private Answer answer;

    @OneToOne
    @JoinColumn(name = "student_answer_id", referencedColumnName = "id")
    private StudentAnswer studentAnswer;

    public Question() {}

    public Question(String questionText, String questionGroup, Exercise exercise) {
        this.questionText = questionText;
        this.questionGroup = questionGroup;
        this.exercise = exercise;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionGroup() {
        return questionGroup;
    }

    public void setQuestionGroup(String questionGroup) {
        this.questionGroup = questionGroup;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public StudentAnswer getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(StudentAnswer studentAnswer) {
        this.studentAnswer = studentAnswer;
    }
}