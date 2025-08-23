package itopia.resolar.domain.page.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.util.List;

@Schema(description = "대규모 하이라이트 페이지 생성 요청")
public record LargeHighlightPageCreateRequest(
        @Schema(description = "페이지 URL", example = "https://spring.io/guides/gs/rest-service/", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "URL은 필수입니다")
        @Pattern(regexp = "^https?://.*", message = "올바른 URL 형식이어야 합니다")
        @JsonProperty("url")
        String url,

        @Schema(description = "페이지 제목", example = "페이지 제목은 뭘까요?", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "페이지 제목은 필수입니다")
        @JsonProperty("title")
        String title,

        @Schema(description = "소속 과목 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "과목 ID는 필수입니다")
        @Positive(message = "과목 ID는 양수여야 합니다")
        Long subjectId,

        @Schema(description = "드래그한 하이라이트 목록", example = "[\"Spring Boot는 마이크로서비스 개발에 적합하다\", \"RESTful API 구축이 간단하다\"]")
        @NotNull(message = "하이라이트 목록은 필수입니다")
        @JsonProperty("highlights")
        List<String> highlights
) {
}