package itopia.resolar.application.external.dto;

public record SummarizeSubjectResponse(
        String success,
        String title,
        String introduction,
        String body,
        String conclusion
) {
}
