package sehmus.school_management_system.payload.mappers;

import lombok.Data;
import org.springframework.stereotype.Component;
import sehmus.school_management_system.models.concretes.Meet;
import sehmus.school_management_system.payload.requests.concretes.MeetingRequest;
import sehmus.school_management_system.payload.responses.concretes.MeetingResponse;

@Component
@Data
public class MeetingMapper {


    public Meet mapMeetingRequestToMeet(MeetingRequest meetingRequest){

        return Meet.builder()
                .date(meetingRequest.getDate())
                .startTime(meetingRequest.getStartTime())
                .stopTime(meetingRequest.getStopTime())
                .description(meetingRequest.getDescription())
                .build();

    }

    public MeetingResponse mapMeetToMeetingResponse(Meet meet){

        return MeetingResponse.builder()
                .id(meet.getId())
                .date(meet.getDate())
                .startTime(meet.getStartTime())
                .stopTime(meet.getStopTime())
                .description(meet.getDescription())
                .advisorTeacherId(meet.getAdvisoryTeacher().getId())
                .teacherSsn(meet.getAdvisoryTeacher().getSsn())
                .teacherName(meet.getAdvisoryTeacher().getName())
                .students(meet.getStudentList())
                .build();

    }
}
