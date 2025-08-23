package itopia.resolar.application.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SummarizeSubjectRequest(
        @JsonProperty("subject")
        String subject,
        @JsonProperty("user_id")
        long userId
) {
}
