package vn.io.rento.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Role extends BaseEntity {
    @Id
    @NonNull
    private String name;

    @NonNull
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @NonNull
    private Set<Permission> permission;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "role")
    @ToString.Exclude
    private Set<User> user;

}

