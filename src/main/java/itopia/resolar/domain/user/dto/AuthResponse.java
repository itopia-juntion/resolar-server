package itopia.resolar.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "인증 응답")
public record AuthResponse(
        @Schema(description = "JWT 액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,
        
        @Schema(description = "사용자명", example = "testuser")
        String username,
        
        @Schema(description = "이메일", example = "test@example.com")
        String email
) {
}