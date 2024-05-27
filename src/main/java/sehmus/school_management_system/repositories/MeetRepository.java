package sehmus.school_management_system.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sehmus.school_management_system.models.concretes.Meet;

import java.util.Collection;
import java.util.List;

public interface MeetRepository extends JpaRepository<Meet, Long> {
    List<Meet> findByStudentList_IdEquals(Long studentId);

    List<Meet> getByAdvisoryTeacher_IdEquals(Long advisoryTeacherId);

    Page<Meet> findByAdvisoryTeacher_IdEquals(Long advisoryTeacherId, Pageable pageable);;
}
