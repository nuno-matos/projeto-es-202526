package pt.estg.projetoes202526.domain;

import lombok.Getter;

@Getter
public enum UserRole {
    TEACHER("Teacher"),
    STUDENT("Student");

    private final String role;

    UserRole(String role){
        this.role = role;
    }
}
