package sehmus.school_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sehmus.school_management_system.models.concretes.EducationTerm;

public interface EducationTermRepository extends JpaRepository<EducationTerm, Long> {
}
