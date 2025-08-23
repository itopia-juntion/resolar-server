package itopia.resolar.domain.user.dto;

public record SignupRequest(
        String username,
        String password,
        String email
) {
}