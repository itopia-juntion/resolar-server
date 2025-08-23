package itopia.resolar.application.external;

import itopia.resolar.application.external.dto.AnalyzeRequest;
import itopia.resolar.application.external.dto.AnalyzeResponse;
import itopia.resolar.application.external.dto.SearchRequest;
import itopia.resolar.application.external.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AiAnalysisClient {

    private final RestClient restClient;

    public AnalyzeResponse analyzeContent(AnalyzeRequest request) {
        try {
            return restClient
                    .post()
                    .uri("/api/v1/analyze")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(AnalyzeResponse.class);
        } catch (RestClientException e) {
            throw new RuntimeException("AI 분석 서비스 호출에 실패했습니다: " + e.getMessage());
        }
    }

    public SearchResponse searchPage(SearchRequest request) {
        try {
            return restClient
                    .post()
                    .uri("/api/v1/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(SearchResponse.class);
        } catch (RestClientException e) {
            throw new RuntimeException("AI 검색 서비스 호출에 실패했습니다: " + e.getMessage());
        }
    }
}