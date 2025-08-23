package itopia.resolar.domain.subject.dto;

import lombok.Builder;

@Builder
public record SubjectResponse(
        long id,
        String name
) {
}
