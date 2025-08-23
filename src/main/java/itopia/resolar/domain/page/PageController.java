package itopia.resolar.domain.page;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import itopia.resolar.domain.page.dto.PageCreateRequest;
import itopia.resolar.domain.page.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
@RequiredArgsConstructor
@Tag(name = "Page", description = "페이지 관리 API")
@SecurityRequirement(name = "JWT")
public class PageController {
    private final PageService pageService;

    @PostMapping
    @Operation(summary = "페이지 생성", description = "페이지를 생성합니다. 지정된 주제에 연결됩니다.")
    @ApiResponse(responseCode = "201", description = "페이지 생성 성공")
    public ResponseEntity<Void> createPage(@Valid @RequestBody PageCreateRequest request) {
        // pageService.createPage(request); // TODO: 구현 필요
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "주제별 페이지 목록 조회", description = "특정 주제에 속한 모든 페이지를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema = true)
    public ResponseEntity<List<PageResponse>> readAllBySubjectId(
            @Parameter(description = "과목 ID", example = "1", required = true)
            @RequestParam long subjectId) {
        List<PageResponse> pages = pageService.readAllBySubjectId(subjectId);
        return ResponseEntity.ok(pages);
    }
}
