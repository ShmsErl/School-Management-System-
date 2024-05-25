package sehmus.school_management_system.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sehmus.school_management_system.models.concretes.Lesson;

import java.util.Optional;

public interface  LessonRepository extends JpaRepository<Lesson,Long> {
    Optional<Lesson> getByLessonNameEqualsIgnoreCase(String lessonName);
}
