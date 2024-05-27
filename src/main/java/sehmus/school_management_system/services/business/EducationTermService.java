package sehmus.school_management_system.services.business;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sehmus.school_management_system.exception.BadRequestException;
import sehmus.school_management_system.exception.ResourceNotFoundException;
import sehmus.school_management_system.models.concretes.EducationTerm;
import sehmus.school_management_system.payload.mappers.EducationTermMapper;
import sehmus.school_management_system.payload.messages.ErrorMessages;
import sehmus.school_management_system.payload.messages.SuccessMessages;
import sehmus.school_management_system.payload.requests.concretes.EducationTermRequest;
import sehmus.school_management_system.payload.responses.concretes.EducationTermResponse;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.repositories.EducationTermRepository;
import sehmus.school_management_system.services.helper.PageableHelper;

@Service
@RequiredArgsConstructor
public class EducationTermService {

    private final EducationTermRepository educationTermRepository;

    private final EducationTermMapper educationTermMapper;

    private final PageableHelper pageableHelper;

    public ResponseMessage<EducationTermResponse> saveEducationTerm(EducationTermRequest educationTermRequest) {

        validateEducationTermDates(educationTermRequest);

        EducationTerm educationTerm = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);

        EducationTerm savedEducationTerm = educationTermRepository.save(educationTerm);

        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_SAVE)
                .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(savedEducationTerm))
                .httpStatus(HttpStatus.CREATED)
                .build();


    }

    private void validateEducationTermDates(EducationTermRequest educationTermRequest){

        validateEducationTermDatesForRequests(educationTermRequest);

        if (educationTermRepository.existsByTermAndYear(
                educationTermRequest.getTerm(), educationTermRequest.getStartDate().getYear()
        )){

            throw new ResourceNotFoundException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);

        }

        if(educationTermRepository.findByYear(educationTermRequest.getStartDate().getYear())
                .stream()
                .anyMatch(educationTerm ->
                        (educationTerm.getStartDate().equals(educationTermRequest.getStartDate())
                                || (educationTerm.getStartDate().isBefore(educationTermRequest.getStartDate())
                                && educationTerm.getEndDate().isAfter(educationTermRequest.getStartDate()))
                                || (educationTerm.getStartDate().isBefore(educationTermRequest.getEndDate())
                                && educationTerm.getEndDate().isAfter(educationTermRequest.getEndDate()))
                                || (educationTerm.getStartDate().isAfter(educationTermRequest.getStartDate())
                                && educationTerm.getEndDate().isBefore(educationTermRequest.getEndDate()))))) {

            throw new BadRequestException(ErrorMessages.EDUCATION_TERM_CONFLICT_MESSAGE);

        }

    }

    private void validateEducationTermDatesForRequests(EducationTermRequest educationTermRequest){

        if (educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())){

            throw new ResourceNotFoundException(ErrorMessages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);

        }

        if (educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())){

            throw new ResourceNotFoundException(ErrorMessages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);

        }

    }

    public EducationTermResponse findById(Long id) {

        EducationTerm educationTerm = isEducationTermExists(id);
        return educationTermMapper.mapEducationTermToEducationTermResponse(educationTerm);

    }

    public EducationTerm isEducationTermExists(Long id){

        return educationTermRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.EDUCATION_TERM_NOT_FOUND_MESSAGE, id)));

    }

    public Page<EducationTermResponse> getAllByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return educationTermRepository.findAll(pageable)
                .map(educationTermMapper::mapEducationTermToEducationTermResponse);

    }

    public ResponseMessage deleteById(Long id) {

        EducationTerm educationTerm = isEducationTermExists(id);
        educationTermRepository.delete(educationTerm);
        return ResponseMessage.builder()
                .message(SuccessMessages.EDUCATION_TERM_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();

    }

    public ResponseMessage<EducationTermResponse> updateEducationTerm(Long id, EducationTermRequest educationTermRequest) {

        isEducationTermExists(id);
        validateEducationTermDatesForRequests(educationTermRequest);

        EducationTerm educationTermToSave = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);
        educationTermToSave.setId(id);

        EducationTerm savedEducationTerm = educationTermRepository.save(educationTermToSave);

        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_UPDATE)
                .httpStatus(HttpStatus.OK)
                .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(savedEducationTerm))
                .build();


    }
}
