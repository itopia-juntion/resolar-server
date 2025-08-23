package itopia.resolar.domain.subject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "과목 생성 요청")
public record SubjectCreateRequest(
        @Schema(description = "주제명", example = "Spring Boot 학습", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "주제명은 필수입니다")
        String name
) {
}