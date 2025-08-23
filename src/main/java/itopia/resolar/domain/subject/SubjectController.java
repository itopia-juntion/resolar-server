package itopia.resolar.domain.subject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import itopia.resolar.domain.subject.dto.SubjectCreateRequest;
import itopia.resolar.domain.subject.dto.SubjectResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
@Tag(name = "Subject", description = "주제 관리 API")
@SecurityRequirement(name = "JWT")
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    @Operation(summary = "주제 생성", description = "새로운 주제를 생성합니다. 현재 로그인한 사용자에게 연결됩니다.")
    @ApiResponse(responseCode = "201", description = "과목 생성 성공", useReturnTypeSchema = true)
    public ResponseEntity<Void> createSubject(@Valid @RequestBody SubjectCreateRequest request) {
        subjectService.createSubject(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "내 주제 목록 조회", description = "현재 로그인한 사용자의 모든 주제를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema = true)
    public ResponseEntity<List<SubjectResponse>> readByUser() {
        List<SubjectResponse> subjects = subjectService.readAllByUserId();
        return ResponseEntity.ok(subjects);
    }
}
