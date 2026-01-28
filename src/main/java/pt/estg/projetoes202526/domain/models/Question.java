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

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    private Answer answer;

    @OneToOne
    @JoinColumn(name = "student_answer_id", referencedColumnName = "id")
    private StudentAnswer studentAnswer;
}