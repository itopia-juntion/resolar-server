package itopia.resolar.application.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record AnalyzeRequest(
        @JsonProperty("subject")
        String subject,

        @JsonProperty("title")
        String title,

        @JsonProperty("url")
        String url,

        @JsonProperty("content")
        String content,

        @JsonProperty("timestamp")
        String timestamp,

        @JsonProperty("id")
        long id
) {
}