package itopia.resolar.application.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SearchRequest(
        @JsonProperty("keyword")
        String keyword,

        @JsonProperty("subjectId")
        String subjectId
) {
}
