package itopia.resolar.domain.page.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "페이지 생성 요청")
public record PageCreateRequest(
        @Schema(description = "페이지 URL", example = "https://spring.io/guides/gs/rest-service/", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "URL은 필수입니다")
        @Pattern(regexp = "^https?://.*", message = "올바른 URL 형식이어야 합니다")
        String url,
        
        @Schema(description = "페이지 요약", example = "Spring Boot RESTful 웹 서비스 구축 가이드", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "요약은 필수입니다")
        String content,

        @Schema(description = "페이지 제목", example = "페이지 제목은 뭘까요?", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "페이지 제목은 필수입니다")
        String title,
        
        @Schema(description = "소속 과목 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "과목 ID는 필수입니다")
        @Positive(message = "과목 ID는 양수여야 합니다")
        Long subjectId
) {
}