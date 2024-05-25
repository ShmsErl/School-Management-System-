package sehmus.school_management_system.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sehmus.school_management_system.models.concretes.Lesson;

public interface  LessonRepository extends JpaRepository<Lesson,Long> {
}
