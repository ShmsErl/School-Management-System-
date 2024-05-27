package sehmus.school_management_system.services.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sehmus.school_management_system.models.concretes.User;
import sehmus.school_management_system.models.enums.RoleType;
import sehmus.school_management_system.payload.mappers.UserMapper;
import sehmus.school_management_system.payload.messages.SuccessMessages;
import sehmus.school_management_system.payload.requests.concretes.StudentRequest;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.payload.responses.concretes.StudentResponse;
import sehmus.school_management_system.repositories.UserRepository;
import sehmus.school_management_system.services.business.LessonProgramService;
import sehmus.school_management_system.services.helper.MethodHelper;
import sehmus.school_management_system.services.validator.DateTimeValidator;
import sehmus.school_management_system.services.validator.UniquePropertyValidator;

@Service
@RequiredArgsConstructor
public class StudentService {


    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    private final UniquePropertyValidator uniquePropertyValidator;

    private final PasswordEncoder passwordEncoder;

    private final LessonProgramService lessonProgramService;

    private final DateTimeValidator dateTimeValidator;

    private final MethodHelper methodHelper;

    private final UserMapper userMapper;



    public ResponseMessage<StudentResponse> saveStudent(StudentRequest studentRequest) {

        User advisorTeacher = methodHelper.isUserExist(studentRequest.getAdvisorTeacherId());

        methodHelper.checkIsAdvisor(advisorTeacher);

        uniquePropertyValidator.checkDuplicate(
                studentRequest.getUsername(),
                studentRequest.getSsn(),
                studentRequest.getPhoneNumber(),
                studentRequest.getEmail()

        );

        User student = userMapper.mapUserRequestToUser(studentRequest);

        student.setAdvisorTeacherId(advisorTeacher.getId());
        student.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        student.setActive(true);
        student.setIsAdvisor(false);
        student.setStudentNumber(getLastNumber());


        return ResponseMessage.<StudentResponse>builder()
                .returnBody(userMapper.mapUserToStudentResponse(userRepository.save(student)))
                .message(SuccessMessages.STUDENT_SAVE)
                .httpStatus(HttpStatus.OK)
                .build();

    }

}
