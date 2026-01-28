package pt.estg.projetoes202526.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Answer {
    @Id
    private Integer id;
    private String answerText;

    @OneToOne(mappedBy = "answer")
    private Question question;
}