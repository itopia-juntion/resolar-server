package itopia.resolar.application.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SummarizeSubjectResponse(
        @JsonProperty("success")
        String success,

        @JsonProperty("title")
        String title,

        @JsonProperty("introduction")
        String introduction,

        @JsonProperty("body")
        String body,

        @JsonProperty("conclusion")
        String conclusion
) {
}
