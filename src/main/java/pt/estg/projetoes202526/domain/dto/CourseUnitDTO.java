package pt.estg.projetoes202526.domain.dto;

import java.util.List;

public record CourseUnitDTO(String name, List<ExerciseInfoDTO> exercises) {}