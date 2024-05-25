package sehmus.school_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sehmus.school_management_system.models.concretes.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

}
