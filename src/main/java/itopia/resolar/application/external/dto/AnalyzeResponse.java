package itopia.resolar.application.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AnalyzeResponse(
        @JsonProperty("pageId")
        long pageId,

        @JsonProperty("summary")
        String summary,

        @JsonProperty("importance")
        Double importance
) {
}