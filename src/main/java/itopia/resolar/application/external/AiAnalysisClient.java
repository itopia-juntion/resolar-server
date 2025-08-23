package itopia.resolar.application.external;

import itopia.resolar.application.external.dto.AnalyzeRequest;
import itopia.resolar.application.external.dto.AnalyzeResponse;
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

//    public void searchPage(String keyword, long subjectId) {
//        try {
//            AnalyzeRequest request = new AnalyzeRequest(url, content);
//
//            return restClient
//                    .post()
//                    .uri("/api/v1/analyze")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(request)
//                    .retrieve()
//                    .body(AnalyzeResponse.class);
//        } catch (RestClientException e) {
//            log.error("Failed to analyze content for URL: {}", url, e);
//            throw new RuntimeException("AI 분석 서비스 호출에 실패했습니다: " + e.getMessage());
//        }
//    }
}