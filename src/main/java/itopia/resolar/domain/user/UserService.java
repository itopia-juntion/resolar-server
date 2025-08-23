package itopia.resolar.domain.user;

import itopia.resolar.application.config.JwtUtil;
import itopia.resolar.domain.user.dto.AuthRequest;
import itopia.resolar.domain.user.dto.AuthResponse;
import itopia.resolar.domain.user.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("이미 존재하는 사용자명입니다");
        }
        
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("이미 존재하는 이메일입니다");
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = User.builder()
                .username(request.username())
                .password(encodedPassword)
                .email(request.email())
                .build();

        User savedUser = userRepository.save(user);
        String accessToken = jwtUtil.createAccessToken(savedUser.getUsername());

        return new AuthResponse(accessToken, savedUser.getUsername(), savedUser.getEmail());
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("잘못된 비밀번호입니다");
        }

        String accessToken = jwtUtil.createAccessToken(user.getUsername());
        return new AuthResponse(accessToken, user.getUsername(), user.getEmail());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
