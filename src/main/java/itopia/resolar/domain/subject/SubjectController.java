package itopia.resolar.domain.subject;

import itopia.resolar.domain.page.dto.PageResponse;
import itopia.resolar.domain.subject.dto.SubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<Void> createSubject(@RequestParam String name) {
        subjectService.createSubject(name);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponse>> readByUser() {
        List<SubjectResponse> subjects = subjectService.readAllByUserId();
        return ResponseEntity.ok(subjects);
    }
}
