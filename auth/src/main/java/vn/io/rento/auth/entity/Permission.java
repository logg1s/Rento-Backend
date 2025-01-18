package vn.io.rento.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Permission extends BaseEntity {
    @Id
    @NonNull
    private String name;

    @NonNull
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Role> role;
}
