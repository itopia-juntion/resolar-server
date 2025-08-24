package itopia.resolar.domain.subject;

import itopia.resolar.application.external.AiAnalysisClient;
import itopia.resolar.application.external.dto.SearchRequest;
import itopia.resolar.application.external.dto.SearchResponse;
import itopia.resolar.application.external.dto.SummarizeSubjectRequest;
import itopia.resolar.application.external.dto.SummarizeSubjectResponse;
import itopia.resolar.application.pdf.PdfService;
import itopia.resolar.application.security.SecurityUtil;
import itopia.resolar.domain.page.Page;
import itopia.resolar.domain.page.PageRepository;
import itopia.resolar.domain.page.dto.PageResponse;
import itopia.resolar.domain.subject.dto.SubjectResponse;
import itopia.resolar.domain.user.User;
import itopia.resolar.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final PageRepository pageRepository;
    private final UserRepository userRepository;
    private final AiAnalysisClient aiAnalysisClient;
    private final PdfService pdfService;

    public SubjectResponse createSubject(String name) {
        long userId = SecurityUtil.getCurrentUserId();
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Subject subject = Subject.builder()
                .name(name)
                .user(currentUser)
                .build();

        Subject savedSubject = subjectRepository.save(subject);

        return SubjectResponse.builder()
                .id(savedSubject.getId())
                .name(savedSubject.getName())
                .build();
    }

    public byte[] summarizeSubject(long subjectId) {
        long userId = SecurityUtil.getCurrentUserId();
        Subject subject = subjectRepository.findByIdAndUserId(subjectId, userId)
                .orElseThrow(() -> new RuntimeException("해당 주제를 찾을 수 없습니다."));

        List<Page> pages = pageRepository.findAllBySubjectIdAndUserIdOrderByIdDesc(subjectId, userId);

        if (pages.isEmpty()) {
            throw new RuntimeException("해당 주제에 저장된 페이지가 없습니다.");
        }

        try {
            SummarizeSubjectRequest request = new SummarizeSubjectRequest(subject.getName(), userId);
            SummarizeSubjectResponse response = aiAnalysisClient.summarizeSubject(request);

            return pdfService.generateSubjectReportPdf(subject.getName(), response);
        } catch (Exception e) {
            throw new RuntimeException("주제 요약 PDF 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public PageResponse searchByKeyword(String keyword, long subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if (subject.isEmpty()) {
            throw new RuntimeException("해당 주제를 찾을 수 없습니다.");
        }

        long currentUserId = SecurityUtil.getCurrentUserId();

        SearchRequest request = new SearchRequest(keyword, subject.get().getName(), 1, currentUserId);
        SearchResponse response = aiAnalysisClient.searchPage(request);
        Page page = pageRepository.findById(response.id())
                .orElse(null);

        return PageResponse.from(page);
    }

    public List<SubjectResponse> readAllByUserId() {
        long userId = SecurityUtil.getCurrentUserId();
        return subjectRepository.findByUserId(userId)
                .stream()
                .map(SubjectResponse::from)
                .toList();
    }

    public void deleteSubject(long subjectId) {
        long userId = SecurityUtil.getCurrentUserId();
        Subject subject = subjectRepository.findByIdAndUserId(subjectId, userId)
                .orElseThrow(() -> new RuntimeException("해당 주제가 없습니다."));
        subjectRepository.delete(subject);
    }
}
