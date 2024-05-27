package sehmus.school_management_system.services.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sehmus.school_management_system.payload.mappers.UserMapper;
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
}
