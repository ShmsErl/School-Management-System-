package sehmus.school_management_system.controller.business;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sehmus.school_management_system.payload.requests.concretes.EducationTermRequest;
import sehmus.school_management_system.payload.responses.concretes.EducationTermResponse;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.services.business.EducationTermService;

import java.util.List;

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

    @PreAuthorize("hasAnyAuthority('Admin','Dean', 'ViceDean', 'Teacher')")
    @GetMapping("/getAllEducationTermsByPage")
    public Page<EducationTermResponse> getAllEducationTermsByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "startDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){

        return educationTermService.getAllByPage(page,size,sort,type);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean', 'ViceDean', 'Teacher')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteEducationTermById(@PathVariable Long id){

        return educationTermService.deleteById(id);

    }

}
