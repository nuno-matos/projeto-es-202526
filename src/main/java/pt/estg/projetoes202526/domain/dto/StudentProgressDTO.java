package pt.estg.projetoes202526.domain.dto;

public record StudentProgressDTO(
        Integer id,
        String studentName,
        Integer currentPhase,
        Integer totalPhases,
        boolean helpRequested,
        boolean finished,
        Double grade
) {}