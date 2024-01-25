package ra.spring_security_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.spring_security_jwt.model.ERoles;
import ra.spring_security_jwt.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERoles eRoles);
}
