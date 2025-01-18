package vn.io.rento.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Role extends BaseEntity {
    @Id
    @NonNull
    private String name;

    @NonNull
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_name"), inverseJoinColumns = @JoinColumn(name = "permission_name"))
    @NonNull
    private Set<Permission> permissions;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> users;
}
