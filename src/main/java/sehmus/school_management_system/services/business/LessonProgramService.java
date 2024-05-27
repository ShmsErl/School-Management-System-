package sehmus.school_management_system.services.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sehmus.school_management_system.exception.BadRequestException;
import sehmus.school_management_system.exception.ResourceNotFoundException;
import sehmus.school_management_system.models.concretes.EducationTerm;
import sehmus.school_management_system.models.concretes.Lesson;
import sehmus.school_management_system.models.concretes.LessonProgram;
import sehmus.school_management_system.models.concretes.User;
import sehmus.school_management_system.models.enums.RoleType;
import sehmus.school_management_system.payload.mappers.LessonProgramMapper;
import sehmus.school_management_system.payload.messages.ErrorMessages;
import sehmus.school_management_system.payload.messages.SuccessMessages;
import sehmus.school_management_system.payload.requests.concretes.LessonProgramRequest;
import sehmus.school_management_system.payload.responses.concretes.LessonProgramResponse;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.repositories.LessonProgramRepository;
import sehmus.school_management_system.services.helper.MethodHelper;
import sehmus.school_management_system.services.helper.PageableHelper;
import sehmus.school_management_system.services.validator.DateTimeValidator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;

    private final LessonService lessonService;

    private final  EducationTermService educationTermService;

    private final DateTimeValidator dateTimeValidator;

    private final LessonProgramMapper lessonProgramMapper;

    private final PageableHelper pageableHelper;

    private final MethodHelper methodHelper;

    private LessonProgram isLessonProgramExist(Long id){

        return lessonProgramRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_PROGRAM_MESSAGE, id))  );


    }

    public ResponseMessage<LessonProgramResponse> saveLessonProgram(LessonProgramRequest lessonProgramRequest) {

        Set<Lesson> lessons = lessonService.getLessonByIdSet(lessonProgramRequest.getLessonIdList());

        EducationTerm educationTerm = educationTermService
                .isEducationTermExists(lessonProgramRequest.getEducationTermId());

        dateTimeValidator.checkTimeWithException(lessonProgramRequest.getStartTime(),
                lessonProgramRequest.getStopTime());

        LessonProgram lessonProgram = lessonProgramMapper.mapLessonProgramRequestToLessonProgram(lessonProgramRequest, lessons, educationTerm);

        LessonProgram savedLessonProgram = lessonProgramRepository.save(lessonProgram);

        return ResponseMessage.<LessonProgramResponse>builder()
                .message(SuccessMessages.LESSON_PROGRAM_SAVE)
                .returnBody(lessonProgramMapper.mapLessonProgramToLessonProgramResponse(savedLessonProgram))
                .httpStatus(HttpStatus.CREATED)
                .build();


    }

    public List<LessonProgramResponse> getAll() {

        return lessonProgramRepository.findAll().stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse).collect(Collectors.toList());

    }

    public List<LessonProgramResponse> getAllUnassigned() {

        return lessonProgramRepository.findByUsers_IdNull().stream().map(
                lessonProgramMapper::mapLessonProgramToLessonProgramResponse
        ).collect(Collectors.toList());

    }

    public List<LessonProgramResponse> getAllAssigned() {

        return lessonProgramRepository.findByUsers_IdNotNull().stream().map(
                lessonProgramMapper::mapLessonProgramToLessonProgramResponse
        ).collect(Collectors.toList());

    }

    public LessonProgramResponse getLessonProgramById(Long id) {

        return lessonProgramMapper.mapLessonProgramToLessonProgramResponse(isLessonProgramExist(id));

    }


    public ResponseMessage deleteById(Long id) {

        isLessonProgramExist(id);

        lessonProgramRepository.deleteById(id);

        return ResponseMessage.builder()
                .message(SuccessMessages.LESSON_PROGRAM_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();

    }

    public Page<LessonProgramResponse> getLessonProgramByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return lessonProgramRepository.findAll(pageable)
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse);

    }

    public Set<LessonProgram> getLessonProgramById(Set<Long> lessonIdSet){

        Set<LessonProgram> lessonProgramSet = lessonProgramRepository.getLessonProgramByIdList(lessonIdSet);
        if (lessonProgramSet.isEmpty()){
            throw new BadRequestException(ErrorMessages.NOT_FOUND_LESSON_PROGRAM_MESSAGE_WITHOUT_ID_INFO);
        }

        return lessonProgramSet;

    }

    public Set<LessonProgramResponse> getAllLessonProgramByTeacherUsername(HttpServletRequest httpServletRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        return lessonProgramRepository.getLessonProgramByUsername(username)
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toSet());

    }

    public Set<LessonProgramResponse> getAllLessonProgramByTeacherId(Long teacherId) {

        User teacher = methodHelper.isUserExist(teacherId);
        methodHelper.checkRole(teacher, RoleType.TEACHER);

        return lessonProgramRepository.findByUsers_IdEquals(teacherId)
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toSet());

    }


    public Set<LessonProgramResponse> getAllLessonProgramByStudentId(Long studentId) {

        User student = methodHelper.isUserExist(studentId);
        methodHelper.checkRole(student, RoleType.STUDENT);

        return lessonProgramRepository.findByUsers_IdEquals(studentId)
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toSet());

    }
}
