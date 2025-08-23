package itopia.resolar.domain.page.dto;

import itopia.resolar.domain.page.Page;

public record PageResponse(
        long id,
        String url,
        String summary,
        int importance,
        long subjectId
) {
    public static PageResponse from(Page page) {
        return new PageResponse(
                page.getId(),
                page.getUrl(),
                page.getSummary(),
                page.getImportance(),
                page.getSubject().getId()
        );
    }
}
