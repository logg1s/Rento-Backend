package vn.io.rento.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Permission extends BaseEntity {
    @Id
    @NonNull
    private String name;

    @NonNull
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permission")
    @ToString.Exclude
    private Set<Role> role;
}
