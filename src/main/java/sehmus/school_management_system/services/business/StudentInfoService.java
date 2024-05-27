package sehmus.school_management_system.services.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sehmus.school_management_system.exception.ConflictException;
import sehmus.school_management_system.exception.ResourceNotFoundException;
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
import sehmus.school_management_system.payload.requests.concretes.StudentInfoUpdateRequest;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.payload.responses.concretes.StudentInfoResponse;
import sehmus.school_management_system.repositories.StudentInfoRepository;
import sehmus.school_management_system.services.helper.MethodHelper;
import sehmus.school_management_system.services.helper.PageableHelper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    public StudentInfo isStudentInfoExists(Long id){

        Optional<StudentInfo> studentInfoOptional = studentInfoRepository.findById(id);

        if (studentInfoOptional.isPresent()){

            return studentInfoOptional.get();

        } else throw new ResourceNotFoundException(String.format(ErrorMessages.STUDENT_INFO_NOT_FOUND, id));

    }

    public ResponseMessage<StudentInfoResponse> updateStudentInfo(StudentInfoUpdateRequest studentInfoUpdateRequest, Long studentInfoId) {

        Lesson lesson = lessonService.isLessonExistById(studentInfoUpdateRequest.getLessonId());

        StudentInfo studentInfo = isStudentInfoExists(studentInfoId);

        EducationTerm educationTerm = educationTermService.isEducationTermExists(studentInfoUpdateRequest.getEducationTermId());

        Double noteAverage = calculateExamAverage(studentInfoUpdateRequest.getMidtermExam(), studentInfoUpdateRequest.getFinalExam());

        Note note = checkLetterGrade(noteAverage);

        StudentInfo studentInfoToUpdate = studentInfoMapper.mapStudentInfoUpdateRequestToStudentInfo(
                studentInfoUpdateRequest, studentInfoId, lesson,educationTerm, note, noteAverage
        );

        studentInfoToUpdate.setStudent(studentInfo.getStudent());
        studentInfoToUpdate.setTeacher(studentInfo.getTeacher());

        StudentInfo updatedStudentInfo = studentInfoRepository.save(studentInfoToUpdate);

        return ResponseMessage.<StudentInfoResponse>builder()
                .message(SuccessMessages.STUDENT_INFO_UPDATE)
                .httpStatus(HttpStatus.OK)
                .returnBody(studentInfoMapper.mapStudentInfoToStudentInfoResponse(updatedStudentInfo))
                .build();

    }

    public ResponseMessage deleteStudentInfo(Long id) {

        isStudentInfoExists(id);

        studentInfoRepository.deleteById(id);

        return ResponseMessage.builder()
                .message(SuccessMessages.STUDENT_INFO_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();

    }

    public Page<StudentInfoResponse> findStudentInfoByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return studentInfoRepository.findAll(pageable).map(studentInfoMapper::mapStudentInfoToStudentInfoResponse);

    }

    public StudentInfoResponse findStudentInfoById(Long id) {

        return studentInfoMapper.mapStudentInfoToStudentInfoResponse(isStudentInfoExists(id));

    }

    public List<StudentInfoResponse> findStudentInfoByStudentId(Long studentId) {

        User student = methodHelper.isUserExist(studentId);

        methodHelper.checkRole(student, RoleType.STUDENT);

        return studentInfoRepository.findByStudentId(studentId)
                .stream()
                .map(studentInfoMapper::mapStudentInfoToStudentInfoResponse)
                .collect(Collectors.toList());

    }

    public Page<StudentInfoResponse> getAllByTeacher(int page, int size, HttpServletRequest httpServletRequest) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page,size);

        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadUserByName(username);

        return studentInfoRepository.findByTeacherUsername(username, pageable)
                .map(studentInfoMapper::mapStudentInfoToStudentInfoResponse);


    }



}
