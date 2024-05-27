package sehmus.school_management_system.services.helper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sehmus.school_management_system.exception.BadRequestException;
import sehmus.school_management_system.models.concretes.Meet;
import sehmus.school_management_system.models.concretes.User;
import sehmus.school_management_system.payload.messages.ErrorMessages;
import sehmus.school_management_system.repositories.MeetRepository;

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
}
