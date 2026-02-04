package pt.estg.projetoes202526.domain.dto;

import java.util.List;

public record ExerciseRegisterDTO(String exerciseTitle, String courseUnitName, List<QuestionDTO> questions) {}