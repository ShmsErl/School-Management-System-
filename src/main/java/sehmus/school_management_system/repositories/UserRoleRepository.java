package sehmus.school_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sehmus.school_management_system.models.concretes.UserRole;
import sehmus.school_management_system.models.enums.RoleType;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    @Query("SELECT r FROM UserRole r WHERE r.roleType = ?1")
    Optional<UserRole> findByEnumRoleEquals(RoleType roleType);
}
