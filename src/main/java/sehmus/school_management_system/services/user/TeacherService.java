package sehmus.school_management_system.services.user;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sehmus.school_management_system.models.concretes.LessonProgram;
import sehmus.school_management_system.models.concretes.User;
import sehmus.school_management_system.models.enums.RoleType;
import sehmus.school_management_system.payload.mappers.UserMapper;
import sehmus.school_management_system.payload.messages.SuccessMessages;
import sehmus.school_management_system.payload.requests.concretes.AddLessonProgramToTeacherRequest;
import sehmus.school_management_system.payload.requests.concretes.TeacherRequest;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.payload.responses.concretes.StudentResponse;
import sehmus.school_management_system.payload.responses.concretes.UserResponse;
import sehmus.school_management_system.repositories.UserRepository;
import sehmus.school_management_system.services.business.LessonProgramService;
import sehmus.school_management_system.services.helper.MethodHelper;
import sehmus.school_management_system.services.validator.DateTimeValidator;
import sehmus.school_management_system.services.validator.UniquePropertyValidator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    private final UniquePropertyValidator uniquePropertyValidator;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final LessonProgramService lessonProgramService;

    private final MethodHelper methodHelper;

    private final DateTimeValidator dateTimeValidator;






    @Transactional
    public ResponseMessage<UserResponse> saveTeacher(TeacherRequest teacherRequest) {

        Set<LessonProgram> lessonProgramSet =
                lessonProgramService.getLessonProgramById(teacherRequest.getLessonProgramIdList());

        uniquePropertyValidator.checkDuplicate(
                teacherRequest.getUsername(),
                teacherRequest.getSsn(),
                teacherRequest.getPhoneNumber(),
                teacherRequest.getEmail()
        );

        User teacher = userMapper.mapUserRequestToUser(teacherRequest);

        teacher.setIsAdvisor(teacherRequest.getIsAdvisorTeacher());
        teacher.setLessonProgramList(lessonProgramSet);
        teacher.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));

        User savedTeacher = userRepository.save(teacher);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.TEACHER_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .returnBody(userMapper.mapUserToUserResponse(savedTeacher))
                .build();
    }

    public ResponseMessage<UserResponse> changeAdvisorTeacherStatus(Long id) {

        User teacher = methodHelper.isUserExist(id);
        methodHelper.checkRole(teacher, RoleType.TEACHER);
        methodHelper.checkIsAdvisor(teacher);

        teacher.setIsAdvisor(false);
        userRepository.save(teacher);

        List<User> allStudents = userRepository.findByAdvisorTeacherId(id);

        if (!allStudents.isEmpty()){

            allStudents.forEach(students -> students.setAdvisorTeacherId(null));

        }

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.ADVISOR_TEACHER_DELETE)
                .returnBody(userMapper.mapUserToUserResponse(teacher))
                .httpStatus(HttpStatus.OK)
                .build();

    }

    public List<UserResponse> getAllAdvisorTeacher() {

        return userRepository.findAllAdvisorTeacher()
                .stream()
                .map(userMapper::mapUserToUserResponse)
                .collect(Collectors.toList());

    }

    public ResponseMessage<UserResponse> updateTeacher(TeacherRequest teacherRequest, Long userId) {

        User teacher = methodHelper.isUserExist(userId);
        methodHelper.checkRole(teacher, RoleType.TEACHER);

        // TODO: after update IsAdvisorTeacher become false. How to fix it.

        Set<LessonProgram> lessonProgramSet =
                lessonProgramService.getLessonProgramById(teacherRequest.getLessonProgramIdList());

        User teacherToSave = userMapper.mapUserRequestToUser(teacherRequest);
        teacherToSave.setId(teacher.getId());
        teacherToSave.setLessonProgramList(lessonProgramSet);
        teacherToSave.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));

        User savedTeacher = userRepository.save(teacherToSave);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.TEACHER_UPDATE)
                .returnBody(userMapper.mapUserToUserResponse(savedTeacher))
                .httpStatus(HttpStatus.OK)
                .build();


    }

    public List<StudentResponse> getAllStudentByAdvisorTeacher(HttpServletRequest httpServletRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadUserByName(username);
        methodHelper.checkIsAdvisor(teacher);
        return userRepository.findByAdvisorTeacherId(teacher.getId())
                .stream()
                .map(userMapper::mapUserToStudentResponse)
                .collect(Collectors.toList());

    }

    public ResponseMessage<UserResponse> addLessonProgramToTeacher(AddLessonProgramToTeacherRequest addLessonProgramToTeacherRequest) {

        User teacher = methodHelper.isUserExist(addLessonProgramToTeacherRequest.getTeacherId());
        methodHelper.checkRole(teacher, RoleType.TEACHER);
        Set<LessonProgram> existingLessonPrograms = teacher.getLessonProgramList();

        Set<LessonProgram> requestedLessonPrograms =
                lessonProgramService.getLessonProgramById(addLessonProgramToTeacherRequest.getLessonProgramId());

        dateTimeValidator.checkLessonPrograms(existingLessonPrograms, requestedLessonPrograms);
        existingLessonPrograms.addAll(requestedLessonPrograms);
        teacher.setLessonProgramList(existingLessonPrograms);
        User updatedTeacher = userRepository.save(teacher);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.LESSON_PROGRAM_ADD_TO_TEACHER)
                .httpStatus(HttpStatus.OK)
                .returnBody(userMapper.mapUserToUserResponse(updatedTeacher))
                .build();


    }



}
