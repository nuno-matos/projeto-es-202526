package pt.estg.projetoes202526.domain.dto;

import pt.estg.projetoes202526.domain.UserRole;

public record RegisterDTO(String email, String password, String username, UserRole role) {}