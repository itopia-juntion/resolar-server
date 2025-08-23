package itopia.resolar.domain.subject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import itopia.resolar.domain.subject.Subject;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(description = "과목 응답")
public record SubjectResponse(
        @Schema(description = "주제 ID", example = "1")
        long id,
        
        @Schema(description = "주제명", example = "Spring Boot 학습")
        String name,
        
        @Schema(description = "생성일시", example = "2023-12-01T10:00:00")
        LocalDateTime createdAt,
        
        @Schema(description = "수정일시", example = "2023-12-01T15:30:00")
        LocalDateTime updatedAt
) {
    public static SubjectResponse from(Subject subject) {
        return new SubjectResponse(
                subject.getId(),
                subject.getName(),
                subject.getCreatedAt(),
                subject.getUpdatedAt()
        );
    }
}
