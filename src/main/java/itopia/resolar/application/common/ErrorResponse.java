package itopia.resolar.application.common;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "에러 응답")
public record ErrorResponse(
        @Schema(description = "에러 메시지", example = "사용자를 찾을 수 없습니다")
        String message,
        
        @Schema(description = "에러 발생 시간", example = "2023-12-01T10:00:00")
        LocalDateTime timestamp
) {
    public static ErrorResponse of(String message) {
        return new ErrorResponse(message, LocalDateTime.now());
    }
}