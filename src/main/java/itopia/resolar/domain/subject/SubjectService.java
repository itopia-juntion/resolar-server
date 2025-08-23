package itopia.resolar.domain.subject;

import itopia.resolar.application.util.SecurityUtil;
import itopia.resolar.domain.subject.dto.SubjectResponse;
import itopia.resolar.domain.user.User;
import itopia.resolar.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public SubjectResponse createSubject(String name) {
        long userId = SecurityUtil.getCurrentUserId();
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Subject subject = Subject.builder()
                .name(name)
                .user(currentUser)
                .build();

        Subject savedSubject = subjectRepository.save(subject);

        return SubjectResponse.builder()
                .id(savedSubject.getId())
                .name(savedSubject.getName())
                .build();
    }

    public List<SubjectResponse> readAllByUserId() {
        long userId = SecurityUtil.getCurrentUserId();
        return subjectRepository.findByUserId(userId)
                .stream()
                .map(SubjectResponse::from)
                .toList();
    }
}
