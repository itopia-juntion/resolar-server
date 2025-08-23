package itopia.resolar.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "회원가입 요청")
public record SignupRequest(
        @Schema(description = "사용자명", example = "testuser", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "사용자명은 필수입니다")
        @Size(min = 3, max = 20, message = "사용자명은 3-20자 사이여야 합니다")
        String username,
        
        @Schema(description = "비밀번호", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 8, max = 100, message = "비밀번호는 8자 이상이어야 합니다")
        String password,
        
        @Schema(description = "이메일", example = "test@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "올바른 이메일 형식이어야 합니다")
        String email
) {
}