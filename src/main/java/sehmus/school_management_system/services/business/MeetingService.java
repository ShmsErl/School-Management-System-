package sehmus.school_management_system.services.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sehmus.school_management_system.models.concretes.Meet;
import sehmus.school_management_system.models.concretes.User;
import sehmus.school_management_system.payload.mappers.MeetingMapper;
import sehmus.school_management_system.payload.messages.SuccessMessages;
import sehmus.school_management_system.payload.requests.concretes.MeetingRequest;
import sehmus.school_management_system.payload.responses.concretes.MeetingResponse;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.repositories.MeetRepository;
import sehmus.school_management_system.services.helper.MeetingHelper;
import sehmus.school_management_system.services.helper.MethodHelper;
import sehmus.school_management_system.services.helper.PageableHelper;
import sehmus.school_management_system.services.validator.DateTimeValidator;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MeetingService {

    private final MeetRepository meetingRepository;

    //private final UserService userService;

    private final MethodHelper methodHelper;

    private final DateTimeValidator dateTimeValidator;

    private final MeetingMapper meetingMapper;

    private final MeetingHelper meetingHelper;

    private final PageableHelper pageableHelper;


    public ResponseMessage<MeetingResponse> saveMeeting(HttpServletRequest httpServletRequest,
                                                        MeetingRequest meetingRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadUserByName(username);
        methodHelper.checkIsAdvisor(teacher);
        dateTimeValidator.checkTimeWithException(meetingRequest.getStartTime(), meetingRequest.getStopTime());

        meetingHelper.checkMeetingConflicts(meetingRequest.getStudentIds(),
                teacher.getId(),
                meetingRequest.getDate(),
                meetingRequest.getStartTime(),
                meetingRequest.getStopTime());

        List<User> students = methodHelper.getUserList(meetingRequest.getStudentIds());

        Meet meet = meetingMapper.mapMeetingRequestToMeet(meetingRequest);
        meet.setStudentList(students);
        meet.setAdvisoryTeacher(teacher);

        Meet savedMeet = meetingRepository.save(meet);

        return ResponseMessage.<MeetingResponse>builder()
                .message(SuccessMessages.MEET_SAVE)
                .returnBody(meetingMapper.mapMeetToMeetingResponse(savedMeet))
                .httpStatus(HttpStatus.OK)
                .build();


    }

}
