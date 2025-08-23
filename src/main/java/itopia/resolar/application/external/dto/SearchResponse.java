package itopia.resolar.application.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SearchResponse(
        @JsonProperty("success")
        boolean success,

        @JsonProperty("answer")
        String answer,

        @JsonProperty("url")
        String url,

        @JsonProperty("title")
        String title,

        @JsonProperty("id")
        long id
) {
}
