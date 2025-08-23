package itopia.resolar.application.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SearchRequest(
        @JsonProperty("query")
        String query,

        @JsonProperty("subjectId")
        String subjectId,

        @JsonProperty("limit")
        int limit,

        @JsonProperty("user_id")
        long userId
) {
}
