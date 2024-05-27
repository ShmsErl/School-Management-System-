package sehmus.school_management_system.payload.mappers;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sehmus.school_management_system.models.concretes.StudentInfo;
import sehmus.school_management_system.models.enums.Note;
import sehmus.school_management_system.payload.requests.concretes.StudentInfoRequest;

@Component
@Data
@RequiredArgsConstructor
public class StudentInfoMapper {


    //private final UserMapper userMapper;

    public StudentInfo mapStudentInfoRequestToStudentInfo(StudentInfoRequest studentInfoRequest,
                                                          Note note, Double average){

        return StudentInfo.builder()
                .infoNote(studentInfoRequest.getInfoNote())
                .absentee(studentInfoRequest.getAbsentee())
                .midtermExam(studentInfoRequest.getMidtermExam())
                .finalExam(studentInfoRequest.getFinalExam())
                .examAverage(average)
                .letterGrade(note)
                .build();

    }

}
