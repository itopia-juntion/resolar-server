package itopia.resolar.domain.page.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import itopia.resolar.domain.page.Page;

import java.time.LocalDateTime;

@Schema(description = "페이지 응답")
public record PageResponse(
        @Schema(description = "페이지 ID", example = "1")
        long id,
        
        @Schema(description = "페이지 URL", example = "https://spring.io/guides/gs/rest-service/")
        String url,

        @Schema(description = "페이지 제목", example = "밤샘은 위험해")
        String title,
        
        @Schema(description = "페이지 요약", example = "Spring Boot RESTful 웹 서비스 구축 가이드")
        String summary,
        
        @Schema(description = "중요도", example = "5", minimum = "1", maximum = "10")
        double importance,
        
        @Schema(description = "소속 과목 ID", example = "1")
        long subjectId,
        
        @Schema(description = "생성일시", example = "2023-12-01T10:00:00")
        LocalDateTime createdAt,
        
        @Schema(description = "수정일시", example = "2023-12-01T15:30:00")
        LocalDateTime updatedAt
) {
    public static PageResponse from(Page page) {
        return new PageResponse(
                page.getId(),
                page.getUrl(),
                page.getTitle(),
                page.getSummary(),
                page.getImportance(),
                page.getSubject().getId(),
                page.getCreatedAt(),
                page.getUpdatedAt()
        );
    }
}
