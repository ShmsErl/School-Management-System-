package sehmus.school_management_system.services.user;


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
import sehmus.school_management_system.payload.requests.concretes.TeacherRequest;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.payload.responses.concretes.UserResponse;
import sehmus.school_management_system.repositories.UserRepository;
import sehmus.school_management_system.services.business.LessonProgramService;
import sehmus.school_management_system.services.helper.MethodHelper;
import sehmus.school_management_system.services.validator.DateTimeValidator;
import sehmus.school_management_system.services.validator.UniquePropertyValidator;

import java.util.Set;

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


}
