package sehmus.school_management_system.services.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sehmus.school_management_system.repositories.MeetRepository;

@RequiredArgsConstructor
@Component
public class MeetingHelper {
    private final MethodHelper methodHelper;

    private final MeetRepository meetingRepository;
}
