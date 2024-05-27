package sehmus.school_management_system.payload.mappers;

import lombok.Data;
import org.springframework.stereotype.Component;
import sehmus.school_management_system.models.concretes.EducationTerm;
import sehmus.school_management_system.payload.requests.concretes.EducationTermRequest;
import sehmus.school_management_system.payload.responses.concretes.EducationTermResponse;

@Data
@Component
public class EducationTermMapper {


    public EducationTerm mapEducationTermRequestToEducationTerm(EducationTermRequest educationTermRequest){
        return EducationTerm.builder()
                .term(educationTermRequest.getTerm())
                .startDate(educationTermRequest.getStartDate())
                .endDate(educationTermRequest.getEndDate())
                .lastRegistrationDate(educationTermRequest.getLastRegistrationDate())
                .build();
    }

    public EducationTermResponse mapEducationTermToEducationTermResponse(EducationTerm educationTerm){
        return EducationTermResponse.builder()
                .id(educationTerm.getId())
                .term(educationTerm.getTerm())
                .startDate(educationTerm.getStartDate())
                .endDate(educationTerm.getEndDate())
                .lastRegistrationDate(educationTerm.getLastRegistrationDate())
                .build();
    }

}
