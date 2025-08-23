package itopia.resolar.domain.user.dto;

public record AuthRequest(
        String username,
        String password
) {
}
