package itopia.resolar.domain.page;

import itopia.resolar.domain.page.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;

//    @PostMapping

    @GetMapping
    public ResponseEntity<List<PageResponse>> readAllBySubjectId(@RequestParam long subjectId) {
        List<PageResponse> pages = pageService.readAllBySubjectId(subjectId);
        return ResponseEntity.ok(pages);
    }
}
