package sehmus.school_management_system.services.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sehmus.school_management_system.exception.ConflictException;
import sehmus.school_management_system.models.concretes.EducationTerm;
import sehmus.school_management_system.models.concretes.Lesson;
import sehmus.school_management_system.models.concretes.StudentInfo;
import sehmus.school_management_system.models.concretes.User;
import sehmus.school_management_system.models.enums.Note;
import sehmus.school_management_system.models.enums.RoleType;
import sehmus.school_management_system.payload.mappers.StudentInfoMapper;
import sehmus.school_management_system.payload.messages.ErrorMessages;
import sehmus.school_management_system.payload.messages.SuccessMessages;
import sehmus.school_management_system.payload.requests.concretes.StudentInfoRequest;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.payload.responses.concretes.StudentInfoResponse;
import sehmus.school_management_system.repositories.StudentInfoRepository;
import sehmus.school_management_system.services.helper.MethodHelper;
import sehmus.school_management_system.services.helper.PageableHelper;

@Service
@RequiredArgsConstructor
public class StudentInfoService {

    private final StudentInfoRepository studentInfoRepository;

    private final MethodHelper methodHelper;

    private final LessonService lessonService;

    private final EducationTermService educationTermService;

    private final StudentInfoMapper studentInfoMapper;

    private final PageableHelper pageableHelper;

    @Value("${midterm.exam.impact.percentage}")
    private Double midTermExamPercentage;
    @Value("${final.exam.impact.percentage}")
    private Double finalExamPercentage;


    public ResponseMessage<StudentInfoResponse> saveStudentInfo(HttpServletRequest httpServletRequest,
                                                                StudentInfoRequest studentInfoRequest) {

        String teacherUsername = (String) httpServletRequest.getAttribute("username");

        User student = methodHelper.isUserExist(studentInfoRequest.getStudentId());
        methodHelper.checkRole(student, RoleType.STUDENT);
        User teacher = methodHelper.loadUserByName(teacherUsername);
        Lesson lesson = lessonService.isLessonExistById(studentInfoRequest.getLessonId());
        EducationTerm educationTerm = educationTermService.isEducationTermExists(
                studentInfoRequest.getEducationTermId());
        validateLessonDuplication(studentInfoRequest.getStudentId(), lesson.getLessonName());
        Note note = checkLetterGrade(calculateExamAverage(studentInfoRequest.getMidtermExam(),
                studentInfoRequest.getFinalExam()));

        StudentInfo studentInfo = studentInfoMapper.mapStudentInfoRequestToStudentInfo(
                studentInfoRequest,
                note,
                calculateExamAverage(studentInfoRequest.getMidtermExam(),
                        studentInfoRequest.getFinalExam())
        );
        studentInfo.setStudent(student);
        studentInfo.setEducationTerm(educationTerm);
        studentInfo.setTeacher(teacher);
        studentInfo.setLesson(lesson);
        StudentInfo savedStudentInfo = studentInfoRepository.save(studentInfo);

        return ResponseMessage.<StudentInfoResponse>builder()
                .message(SuccessMessages.STUDENT_INFO_SAVE)
                .returnBody(studentInfoMapper.mapStudentInfoToStudentInfoResponse(savedStudentInfo))
                .httpStatus(HttpStatus.OK)
                .build();

    }


    private void validateLessonDuplication(Long studentId, String lessonName){

        if (studentInfoRepository.giveMeDuplications(studentId,lessonName)){

            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_LESSON_MESSAGE, lessonName));

        }



    }

    private Double calculateExamAverage(Double midTermExam, Double finalExam){

        return (midTermExam*midTermExamPercentage) + (finalExam*finalExamPercentage);

    }

    private Note checkLetterGrade(Double average){

        if(average<50.0) {
            return Note.FF;
        } else if (average<60) {
            return Note.DD;
        } else if (average<65) {
            return Note.CC;
        } else if (average<70) {
            return  Note.CB;
        } else if (average<75) {
            return  Note.BB;
        } else if (average<80) {
            return Note.BA;
        } else {
            return Note.AA;
        }
    }

}
