package itopia.resolar.domain.page;

import itopia.resolar.application.external.AiAnalysisClient;
import itopia.resolar.application.external.dto.AnalyzeRequest;
import itopia.resolar.application.external.dto.AnalyzeResponse;
import itopia.resolar.application.security.SecurityUtil;
import itopia.resolar.domain.page.dto.HighlightPageCreateRequest;
import itopia.resolar.domain.page.dto.PageCreateRequest;
import itopia.resolar.domain.page.dto.PageResponse;
import itopia.resolar.domain.subject.Subject;
import itopia.resolar.domain.subject.SubjectRepository;
import itopia.resolar.domain.user.User;
import itopia.resolar.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageService {
    private final PageRepository pageRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final AiAnalysisClient aiAnalysisClient;

    @Transactional
    public PageResponse createPage(PageCreateRequest request) {
        long currentUserId = SecurityUtil.getCurrentUserId();

        Subject subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(() -> new RuntimeException("주제를 찾을 수 없습니다"));

        if (!subject.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("해당 주제에 접근할 권한이 없습니다");
        }

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        Page page = Page.builder()
                .url(request.url())
                .subject(subject)
                .user(currentUser)
                .build();

        Page savedPage = pageRepository.save(page);

        try {
            AnalyzeRequest analyzeRequest = new AnalyzeRequest(
                    subject.getName(),
                    request.title(),
                    request.url(),
                    request.content(),
                    java.time.LocalDateTime.now().toString(),
                    savedPage.getId()
            );
            AnalyzeResponse aiResponse = aiAnalysisClient.analyzeContent(analyzeRequest);

            savedPage.updatePage(aiResponse.summary(), aiResponse.importance());

            return PageResponse.from(savedPage);
        } catch (Exception e) {
            throw new RuntimeException("페이지 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public PageResponse createHighlightPage(HighlightPageCreateRequest request) {
        long currentUserId = SecurityUtil.getCurrentUserId();

        Subject subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(() -> new RuntimeException("주제를 찾을 수 없습니다"));

        if (!subject.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("해당 주제에 접근할 권한이 없습니다");
        }

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        StringBuilder content = new StringBuilder();
        for (String highlight : request.highlights()) {
            content.append(highlight);
            content.append("\n");
        }

        Page page = Page.builder()
                .url(request.url())
                .summary(content.toString())
                .user(currentUser)
                .subject(subject)
                .importance(0)
                .build();

        Page savedPage = pageRepository.save(page);
        return PageResponse.from(savedPage);
    }

    public List<PageResponse> readAllBySubjectId(long subjectId) {
        long currentUserId = SecurityUtil.getCurrentUserId();

        // 과목 권한 확인
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("과목을 찾을 수 없습니다"));

        if (!subject.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("해당 과목에 접근할 권한이 없습니다");
        }

        return pageRepository.findAllBySubjectIdAndUserId(subjectId, currentUserId)
                .stream()
                .map(PageResponse::from)
                .toList();
    }

    public List<PageResponse> searchPagesByKeyword(String keyword) {
        long currentUserId = SecurityUtil.getCurrentUserId();
        
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new RuntimeException("검색어를 입력해주세요");
        }
        
        return pageRepository.findByUserIdAndSummaryContaining(currentUserId, keyword.trim())
                .stream()
                .map(PageResponse::from)
                .toList();
    }

    public List<PageResponse> searchPagesByKeywordAndSubject(String keyword, long subjectId) {
        long currentUserId = SecurityUtil.getCurrentUserId();
        
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new RuntimeException("검색어를 입력해주세요");
        }

        // 과목 권한 확인
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("과목을 찾을 수 없습니다"));

        if (!subject.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("해당 과목에 접근할 권한이 없습니다");
        }

        return pageRepository.findBySubjectIdAndUserIdAndSummaryContaining(subjectId, currentUserId, keyword.trim())
                .stream()
                .map(PageResponse::from)
                .toList();
    }
}
