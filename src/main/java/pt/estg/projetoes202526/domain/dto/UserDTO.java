package pt.estg.projetoes202526.domain.dto;

import pt.estg.projetoes202526.domain.models.Role;

import java.math.BigDecimal;

public record UserDTO(String Name, String email, String password, Role role) {
}
