package vn.io.rento.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class User extends BaseEntity {
    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private Set<Role> role;
}

