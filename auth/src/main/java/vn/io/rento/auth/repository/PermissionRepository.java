package vn.io.rento.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.io.rento.auth.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
