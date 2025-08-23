package itopia.resolar.domain.subject;

import itopia.resolar.domain.subject.dto.SubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectResponse createSubject(String name) {
        Subject subject = Subject.builder()
                .name(name)
                .build();

        Subject savedSubject = subjectRepository.save(subject);

        return SubjectResponse.builder()
                .id(savedSubject.getId())
                .name(savedSubject.getName())
                .build();
    }
}
