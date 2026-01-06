package pt.estg.projetoes202526.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pt.estg.projetoes202526.domain.UserRole;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String name;
    private UserRole role;
    //Metodo para verificar roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.TEACHER) return List.of(new SimpleGrantedAuthority("ROLE_TEACHER"), new SimpleGrantedAuthority("ROLE_STUDENT"));
        else return List.of(new SimpleGrantedAuthority("ROLE_STUDENT"));
    }
    // getUsername is a method of UserDetails maybe conflict with username of our class
    // if this get error implement here and change username atribute
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}