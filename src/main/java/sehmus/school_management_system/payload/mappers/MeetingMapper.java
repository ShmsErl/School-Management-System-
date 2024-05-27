package sehmus.school_management_system.payload.mappers;

import lombok.Data;
import org.springframework.stereotype.Component;
import sehmus.school_management_system.models.concretes.Meet;
import sehmus.school_management_system.payload.requests.concretes.MeetingRequest;

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
}
