package vn.io.rento.auth.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.io.rento.auth.entity.Role;

import java.util.Collection;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    boolean existsAllByNameIn(Collection<String> names);

    List<Role> findAllByNameAndNameNotIn(@NonNull String name, Collection<@NonNull String> name2);
}
