package sehmus.school_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sehmus.school_management_system.models.concretes.LessonProgram;

import java.util.List;
import java.util.Set;

public interface LessonProgramRepository  extends JpaRepository<LessonProgram, Long> {

    List<LessonProgram> findByUsers_IdNull();

    List<LessonProgram> findByUsers_IdNotNull();

    @Query("SELECT l FROM LessonProgram l WHERE l.id IN :idSet")
    Set<LessonProgram> getLessonProgramByIdList(@Param("idSet") Set<Long> idSet);

    @Query("SELECT l FROM LessonProgram l INNER JOIN l.users users WHERE users.username =?1")
    Set<LessonProgram> getLessonProgramByUsername(String username);

    Set<LessonProgram>findByUsers_IdEquals(Long userId);
}
