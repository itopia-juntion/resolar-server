package itopia.resolar.domain.subject;

import itopia.resolar.application.external.AiAnalysisClient;
import itopia.resolar.application.external.dto.SearchRequest;
import itopia.resolar.application.external.dto.SearchResponse;
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

    public PageResponse searchByKeyword(String keyword, long subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if (subject.isEmpty()) {
            throw new RuntimeException("해당 주제를 찾을 수 없습니다.");
        }

        SearchRequest request = new SearchRequest(keyword, subject.get().getName(), 1);
        SearchResponse response = aiAnalysisClient.searchPage(request);
        Page page = pageRepository.findById(response.id())
                .orElseThrow(() -> new RuntimeException("해당 페이지를 찾을 수 없습니다"));

        return PageResponse.from(page);
    }

    public List<SubjectResponse> readAllByUserId() {
        long userId = SecurityUtil.getCurrentUserId();
        return subjectRepository.findByUserId(userId)
                .stream()
                .map(SubjectResponse::from)
                .toList();
    }
}
