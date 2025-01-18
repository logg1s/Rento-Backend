package vn.io.rento.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.io.rento.auth.entity.Role;

import java.util.Collection;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    boolean existsAllByNameIn(Collection<String> names);
}
