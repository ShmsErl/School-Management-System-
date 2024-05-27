package sehmus.school_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sehmus.school_management_system.models.concretes.EducationTerm;
import sehmus.school_management_system.models.enums.Term;

import java.util.List;

public interface EducationTermRepository extends JpaRepository<EducationTerm, Long> {

    @Query("SELECT (count (e) > 0) FROM EducationTerm e WHERE e.term =?1 AND extract(YEAR FROM e.startDate) =?2")
    boolean existsByTermAndYear(Term term, int year);

    @Query("SELECT e FROM EducationTerm e WHERE extract(YEAR FROM e.startDate) =?1")
    List<EducationTerm> findByYear(int year);
}
