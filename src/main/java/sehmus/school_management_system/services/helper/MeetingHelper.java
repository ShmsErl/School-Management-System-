package sehmus.school_management_system.services.helper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sehmus.school_management_system.exception.BadRequestException;
import sehmus.school_management_system.exception.ConflictException;
import sehmus.school_management_system.models.concretes.Meet;
import sehmus.school_management_system.models.concretes.User;
import sehmus.school_management_system.models.enums.RoleType;
import sehmus.school_management_system.payload.messages.ErrorMessages;
import sehmus.school_management_system.repositories.MeetRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class MeetingHelper {
    private final MethodHelper methodHelper;

    private final MeetRepository meetingRepository;



    public void isMeetingMatchedWithTeacher(Meet meet, HttpServletRequest httpServletRequest){

        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadUserByName(username);
        methodHelper.checkIsAdvisor(teacher);

        if (meet.getAdvisoryTeacher().getId().equals(teacher.getId())){

            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);

        }

    }


    public void checkMeetingConflicts(List<Long> studentIdList, Long teacherId, LocalDate meetingDate,
                                      LocalTime startTime, LocalTime stopTime){

        List<Meet> existingMeetings = new ArrayList<>();

        for (Long id:studentIdList){
            ;
            methodHelper.checkRole(methodHelper.isUserExist(id), RoleType.STUDENT);
            existingMeetings.addAll(meetingRepository.findByStudentList_IdEquals(id));

        }

        existingMeetings.addAll(meetingRepository.getByAdvisoryTeacher_IdEquals(teacherId));

        for (Meet meet:existingMeetings){
            LocalTime existingStartTime = meet.getStartTime();
            LocalTime existingStopTime = meet.getStopTime();

            if(meet.getDate().equals(meetingDate) && (
                    (startTime.isAfter(existingStartTime) && startTime.isBefore(existingStopTime)) ||
                            (stopTime.isAfter(existingStartTime) && stopTime.isBefore(existingStopTime)) ||
                            (startTime.isBefore(existingStartTime) && stopTime.isAfter(existingStopTime)) ||
                            (startTime.equals(existingStartTime) || stopTime.equals(existingStopTime))
            )) {
                throw new ConflictException(ErrorMessages.MEET_HOURS_CONFLICT);
            }
        }

    }
}
