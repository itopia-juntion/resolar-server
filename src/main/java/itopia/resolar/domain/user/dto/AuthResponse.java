package itopia.resolar.domain.user.dto;

public record AuthResponse(
        String accessToken,
        String username,
        String email
) {
}