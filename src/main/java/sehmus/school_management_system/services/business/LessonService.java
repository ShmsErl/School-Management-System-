package sehmus.school_management_system.services.business;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sehmus.school_management_system.exception.ResourceNotFoundException;
import sehmus.school_management_system.models.concretes.Lesson;
import sehmus.school_management_system.payload.mappers.LessonMapper;
import sehmus.school_management_system.payload.messages.ErrorMessages;
import sehmus.school_management_system.payload.messages.SuccessMessages;
import sehmus.school_management_system.payload.requests.concretes.LessonRequest;
import sehmus.school_management_system.payload.responses.concretes.LessonResponse;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.repositories.LessonRepository;
import sehmus.school_management_system.services.helper.PageableHelper;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    private final LessonMapper lessonMapper;

    private final PageableHelper pageableHelper;

    public ResponseMessage<LessonResponse> saveLesson(LessonRequest lessonRequest) {

        isLessonExistsByLessonName(lessonRequest.getLessonName());

        Lesson lesson = lessonMapper.mapLessonRequestToLesson(lessonRequest);

        Lesson savedLesson = lessonRepository.save(lesson);

        return ResponseMessage.<LessonResponse>builder().message(SuccessMessages.LESSON_SAVE).httpStatus(HttpStatus.CREATED).returnBody(lessonMapper.mapLessonToLessonResponse(savedLesson)).build();

    }

    public ResponseMessage deleteById(Long id) {

        isLessonExistById(id);

        lessonRepository.deleteById(id);

        return ResponseMessage.builder().message(SuccessMessages.LESSON_DELETE).httpStatus(HttpStatus.OK).build();

    }

    Lesson isLessonExistById(Long id) {

        return lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE, id)));

    }


    private void isLessonExistsByLessonName(String lessonName) {

        if (lessonRepository.getByLessonNameEqualsIgnoreCase(lessonName).isPresent()) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.ALREADY_CREATED_LESSON_MESSAGE, lessonName));
        }

    }

    public ResponseMessage<LessonResponse> getLessonByLessonName(String lessonName) {

        if (lessonRepository.getByLessonNameEqualsIgnoreCase(lessonName).isPresent()) {

            return ResponseMessage.<LessonResponse>builder().message(SuccessMessages.LESSON_FOUND).returnBody(lessonMapper.mapLessonToLessonResponse(lessonRepository.getByLessonNameEqualsIgnoreCase(lessonName).get())).build();

        } else {
            return ResponseMessage.<LessonResponse>builder().message(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE, lessonName)).httpStatus(HttpStatus.NOT_FOUND).build();
        }


    }

    public Page<LessonResponse> findLessonByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return lessonRepository.findAll(pageable).map(lessonMapper::mapLessonToLessonResponse);

    }

    public Set<Lesson> getLessonByIdSet(Set<Long> idSet) {

        return idSet.stream().map(this::isLessonExistById).collect(Collectors.toSet());

    }

    public LessonResponse updateLessonById(Long lessonId, LessonRequest lessonRequest) {

        Lesson lesson = isLessonExistById(lessonId);

        if (!lesson.getLessonName().equals(lessonRequest.getLessonName())) {

            isLessonExistsByLessonName(lessonRequest.getLessonName());

        }

        Lesson updatedLesson = lessonMapper.mapLessonRequestToLesson(lessonRequest);

        updatedLesson.setId(lessonId);
        updatedLesson.setLessonPrograms(lesson.getLessonPrograms());

        Lesson savedLessonProgram = lessonRepository.save(updatedLesson);

        return lessonMapper.mapLessonToLessonResponse(savedLessonProgram);

    }


}
