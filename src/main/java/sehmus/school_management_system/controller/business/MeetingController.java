package sehmus.school_management_system.controller.business;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sehmus.school_management_system.payload.requests.concretes.MeetingRequest;
import sehmus.school_management_system.payload.responses.concretes.MeetingResponse;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.services.business.MeetingService;

import java.util.List;

@RestController
@RequestMapping("/meet")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @PreAuthorize("hasAnyAuthority('Teacher')")
    @PostMapping("/save")
    public ResponseMessage<MeetingResponse> saveMeeting (HttpServletRequest httpServletRequest,
                                                         @Valid @RequestBody MeetingRequest meetingRequest){

        return meetingService.saveMeeting(httpServletRequest, meetingRequest);

    }

    @PreAuthorize("hasAnyAuthority('Teacher')")
    @PutMapping("/update/{meetingId}")
    public ResponseMessage<MeetingResponse> updateMeeting (@RequestBody @Valid MeetingRequest meetingRequest,
                                                           @PathVariable Long meetingId,
                                                           HttpServletRequest httpServletRequest){

        return meetingService.updateMeeting(meetingRequest, meetingId, httpServletRequest);

    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping("/getAll")
    public List<MeetingResponse> getAll(){

        return meetingService.getAll();

    }
}
