package pt.estg.projetoes202526.domain.models;

import jakarta.persistence.*;

@Entity
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String studentAnswerText;

    @OneToOne(mappedBy = "studentAnswer")
    private Question question;
}