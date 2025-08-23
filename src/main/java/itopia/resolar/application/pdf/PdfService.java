package itopia.resolar.application.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import itopia.resolar.application.external.dto.SummarizeSubjectResponse;
import itopia.resolar.domain.page.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfService {

    public byte[] generateSubjectSummaryPdf(String subjectName, List<Page> pages) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // 제목
        Text titleText = new Text(subjectName + " 요약본")
                .setBold()
                .setFontSize(20);
        Paragraph title = new Paragraph(titleText)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(title);

        // 생성 날짜
        Text dateText = new Text("생성일: " + java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .setFontSize(10);
        Paragraph date = new Paragraph(dateText)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginBottom(30);
        document.add(date);

        // 페이지별 요약
        for (int i = 0; i < pages.size(); i++) {
            Page page = pages.get(i);
            
            // 페이지 번호 및 URL
            Text pageNumberText = new Text((i + 1) + ". ")
                    .setBold()
                    .setFontSize(14);
            Text urlText = new Text(page.getUrl())
                    .setFontSize(12)
                    .setItalic();
            Paragraph pageHeader = new Paragraph()
                    .add(pageNumberText)
                    .add(urlText)
                    .setMarginBottom(10);
            document.add(pageHeader);

            // 중요도
            Text importanceText = new Text("중요도: " + page.getImportance() + "/10")
                    .setFontSize(10)
                    .setBold();
            Paragraph importance = new Paragraph(importanceText)
                    .setMarginBottom(5);
            document.add(importance);

            // 요약 내용
            if (page.getSummary() != null && !page.getSummary().isEmpty()) {
                Paragraph summary = new Paragraph(page.getSummary())
                        .setFontSize(11)
                        .setMarginBottom(20);
                document.add(summary);
            }

            // 구분선 (마지막 페이지가 아닌 경우)
            if (i < pages.size() - 1) {
                Paragraph separator = new Paragraph("─".repeat(50))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginBottom(20);
                document.add(separator);
            }
        }

        // 통계 정보
        double averageImportance = pages.stream()
                .mapToDouble(Page::getImportance)
                .average()
                .orElse(0.0);

        Text statsTitle = new Text("\n통계 정보")
                .setBold()
                .setFontSize(16);
        Paragraph statsHeader = new Paragraph(statsTitle)
                .setMarginTop(30)
                .setMarginBottom(10);
        document.add(statsHeader);

        Text statsText = new Text("총 페이지 수: " + pages.size() + "\n평균 중요도: " + String.format("%.1f", averageImportance))
                .setFontSize(12);
        Paragraph stats = new Paragraph(statsText);
        document.add(stats);

        document.close();
        return outputStream.toByteArray();
    }

    public byte[] generateSubjectReportPdf(String subjectName, SummarizeSubjectResponse summary) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // 제목
        Text titleText = new Text(summary.title())
                .setBold()
                .setFontSize(22);
        Paragraph title = new Paragraph(titleText)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        document.add(title);

        // 부제목 (주제명)
        Text subtitleText = new Text("주제: " + subjectName)
                .setFontSize(14)
                .setItalic();
        Paragraph subtitle = new Paragraph(subtitleText)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(30);
        document.add(subtitle);

        // 생성 날짜
        Text dateText = new Text("생성일: " + java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .setFontSize(10);
        Paragraph date = new Paragraph(dateText)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginBottom(40);
        document.add(date);

        // 서론
        if (summary.introduction() != null && !summary.introduction().isEmpty()) {
            Text introTitle = new Text("서론")
                    .setBold()
                    .setFontSize(16);
            Paragraph introTitleParagraph = new Paragraph(introTitle)
                    .setMarginBottom(15);
            document.add(introTitleParagraph);

            Paragraph introduction = new Paragraph(summary.introduction())
                    .setFontSize(12)
                    .setMarginBottom(30);
            document.add(introduction);
        }

        // 본론
        if (summary.body() != null && !summary.body().isEmpty()) {
            Text bodyTitle = new Text("본론")
                    .setBold()
                    .setFontSize(16);
            Paragraph bodyTitleParagraph = new Paragraph(bodyTitle)
                    .setMarginBottom(15);
            document.add(bodyTitleParagraph);

            Paragraph body = new Paragraph(summary.body())
                    .setFontSize(12)
                    .setMarginBottom(30);
            document.add(body);
        }

        // 결론
        if (summary.conclusion() != null && !summary.conclusion().isEmpty()) {
            Text conclusionTitle = new Text("결론")
                    .setBold()
                    .setFontSize(16);
            Paragraph conclusionTitleParagraph = new Paragraph(conclusionTitle)
                    .setMarginBottom(15);
            document.add(conclusionTitleParagraph);

            Paragraph conclusion = new Paragraph(summary.conclusion())
                    .setFontSize(12)
                    .setMarginBottom(30);
            document.add(conclusion);
        }

        document.close();
        return outputStream.toByteArray();
    }
}