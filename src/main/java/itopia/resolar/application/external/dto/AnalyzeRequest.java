package itopia.resolar.application.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AnalyzeRequest(
        @JsonProperty("url")
        String url,
        
        @JsonProperty("content")
        String content
) {
}