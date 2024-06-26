package sehmus.school_management_system.services.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sehmus.school_management_system.exception.ResourceNotFoundException;
import sehmus.school_management_system.models.concretes.Meet;
import sehmus.school_management_system.models.concretes.User;
import sehmus.school_management_system.payload.mappers.MeetingMapper;
import sehmus.school_management_system.payload.messages.ErrorMessages;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MeetingService {

    private final MeetRepository meetingRepository;

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


    public ResponseMessage<MeetingResponse> updateMeeting(MeetingRequest meetingRequest,
                                                          Long meetingId,
                                                          HttpServletRequest httpServletRequest) {
        Meet meet = meetingExistById(meetingId);
        meetingHelper.isMeetingMatchedWithTeacher(meet, httpServletRequest);
        dateTimeValidator.checkTimeWithException(meetingRequest.getStartTime(), meetingRequest.getStopTime());
        meetingHelper.checkMeetingConflicts(meetingRequest.getStudentIds(), meet.getAdvisoryTeacher().getId(),
                meetingRequest.getDate(), meetingRequest.getStartTime(), meetingRequest.getStopTime());

        List<User> students = methodHelper.getUserList(meetingRequest.getStudentIds());

        Meet meetToUpdate = meetingMapper.mapMeetingRequestToMeet(meetingRequest);

        meetToUpdate.setStudentList(students);
        meetToUpdate.setAdvisoryTeacher(meet.getAdvisoryTeacher());
        meetToUpdate.setId(meetingId);

        Meet updatedMeet = meetingRepository.save(meetToUpdate);

        return ResponseMessage.<MeetingResponse>builder()
                .message(SuccessMessages.MEET_UPDATE)
                .httpStatus(HttpStatus.OK)
                .returnBody(meetingMapper.mapMeetToMeetingResponse(updatedMeet))
                .build();

    }

    public List<MeetingResponse> getAll() {

        return meetingRepository.findAll().stream()
                .map(meetingMapper::mapMeetToMeetingResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage deleteById(Long id) {

        meetingExistById(id);
        meetingRepository.deleteById(id);
        return ResponseMessage.builder()
                .message(SuccessMessages.MEET_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Meet meetingExistById(Long id){

        return meetingRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessages.MEET_NOT_FOUND_MESSAGE, id))
        );

    }

    public List<MeetingResponse> getAllMeetingsByLoggedInTeacher(HttpServletRequest httpServletRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadUserByName(username);
        methodHelper.checkIsAdvisor(teacher);

        return meetingRepository.getByAdvisoryTeacher_IdEquals(teacher.getId())
                .stream().map(meetingMapper::mapMeetToMeetingResponse)
                .collect(Collectors.toList());

    }

    public List<MeetingResponse> getAllMeetingsByLoggedInStudent(HttpServletRequest httpServletRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User student = methodHelper.loadUserByName(username);

        return meetingRepository.findByStudentList_IdEquals(student.getId())
                .stream().map(meetingMapper::mapMeetToMeetingResponse)
                .collect(Collectors.toList());

    }

    public Page<MeetingResponse> getAllByPage(int page, int size) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size);

        return meetingRepository.findAll(pageable).map(meetingMapper::mapMeetToMeetingResponse);

    }

    public Page<MeetingResponse> getAllByPageTeacher(HttpServletRequest httpServletRequest, int page, int size) {

        String username = (String) httpServletRequest.getAttribute("username");
        User teacher = methodHelper.loadUserByName(username);
        methodHelper.checkIsAdvisor(teacher);

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size);

        return meetingRepository.findByAdvisoryTeacher_IdEquals(teacher.getId(), pageable)
                .map(meetingMapper::mapMeetToMeetingResponse);

    }

}
