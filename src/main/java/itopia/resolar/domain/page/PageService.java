package itopia.resolar.domain.page;

import itopia.resolar.application.security.SecurityUtil;
import itopia.resolar.domain.page.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PageService {
    private final PageRepository pageRepository;

    public List<PageResponse> readAllBySubjectId(long subjectId) {
        long currentUserId = SecurityUtil.getCurrentUserId();
        return pageRepository.findAllBySubjectIdAndUserId(subjectId, currentUserId)
                .stream()
                .map(PageResponse::from)
                .toList();
    }
}
