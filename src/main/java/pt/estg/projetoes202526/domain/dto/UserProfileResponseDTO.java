package pt.estg.projetoes202526.domain.dto;

import java.util.List;

public record UserProfileResponseDTO(String name, List<CourseUnitDTO> courseUnits) {}