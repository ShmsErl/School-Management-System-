package sehmus.school_management_system.controller.business;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sehmus.school_management_system.payload.requests.concretes.EducationTermRequest;
import sehmus.school_management_system.payload.responses.concretes.EducationTermResponse;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.services.business.EducationTermService;

@RestController
@RequestMapping("/educationTerm")
@RequiredArgsConstructor
public class EducationTermController {

    private final EducationTermService educationTermService ;


    @PreAuthorize("hasAnyAuthority('Admin','Dean')")
    @PostMapping("/save")
    public ResponseMessage<EducationTermResponse> saveEducationTerm(@Valid @RequestBody EducationTermRequest educationTermRequest){

        return educationTermService.saveEducationTerm(educationTermRequest);

    }

    // TODO implement this method.
    @PreAuthorize("hasAnyAuthority('Admin','Dean', 'ViceDean', 'Teacher')")
    @GetMapping("/getAll")
    public List<EducationTermResponse> getAllEducationTerms(){

        return null;

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean', 'ViceDean', 'Teacher')")
    @GetMapping("{id}")
    public EducationTermResponse getEducationTermById(@PathVariable Long id){

        return educationTermService.findById(id);

    }
}
