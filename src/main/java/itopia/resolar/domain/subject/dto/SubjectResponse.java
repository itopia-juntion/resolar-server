package itopia.resolar.domain.subject.dto;

import itopia.resolar.domain.subject.Subject;
import lombok.Builder;

@Builder
public record SubjectResponse(
        long id,
        String name
) {
    public static SubjectResponse from(Subject subject) {
        return new SubjectResponse(
                subject.getId(),
                subject.getName()
        );
    }
}
