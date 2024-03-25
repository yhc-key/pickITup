package com.ssafy.pickitup.domain.recruit.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;


@Document(indexName = "searchrecruit")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecruitDocumentElasticsearch {

    @Id
    private Integer id;

    private String source;
    private String title;
    private String company;
    private String url;
    @Field(name = "thumbnail_url")
    private String thumbnailUrl;
    @Field(name = "qualification_requirements")
    private String qualificationRequirements;
    @Field(name = "preferred_requirements")
    private String preferredRequirements;
    @Field(name = "due_date")
    private String dueDate;
    private String career;
    @Field(name = "collect_time")
    private String collectTime;

    private static int[] parseYearsOfExperienceRange(String careerText) {
        // 정규표현식을 사용하여 경력 범위 추출
        Pattern pattern = Pattern.compile("(\\d+)?(~|\\s*-\\s*)?(\\d+)?\\s*신입|무관|경력무관");
        Matcher matcher = pattern.matcher(careerText);

        if (matcher.find()) {
            if (matcher.group(3) == null) {
                // 시작과 끝이 없는 경우 (e.g., "신입", "무관", "경력무관")
                return new int[]{0, 0};
            } else {
                // 범위가 있는 경우 추출
                int startRange =
                    (matcher.group(1) != null) ? Integer.parseInt(matcher.group(1)) : 0;
                int endRange = (matcher.group(3) != null) ? Integer.parseInt(matcher.group(3))
                    : 100;
                return new int[]{startRange, endRange};
            }
        } else {
            // 그 외의 경우, 단일 값으로 처리 (e.g., "2023년 졸업예정")
            return new int[]{parseSingleYear(careerText), 100};
        }
    }

    private static int parseSingleYear(String careerText) {
        // 정규표현식을 사용하여 연도 추출
        Pattern pattern = Pattern.compile("([0-9]+)년");
        Matcher matcher = pattern.matcher(careerText);

        if (matcher.find()) {
            int year = Integer.parseInt(matcher.group(1));
            if (0 <= year && year < 100) {
                return Integer.parseInt(matcher.group(1));
            } else {
                return 0;
            }
        } else {
            // 연도를 찾을 수 없는 경우 기본값 설정
            return 0;
        }
    }

    public RecruitDocumentMongo toMongo(Integer companyId) {
        return RecruitDocumentMongo.builder()
            .id(this.id)
            .source(this.source)
            .title(this.title)
            .companyId(companyId)
            .company(this.company)
            .url(this.url)
            .thumbnailUrl(this.thumbnailUrl)
            .qualificationRequirements(new HashSet<>())
            .preferredRequirements(new HashSet<>())
            .dueDate(parseDate(this.dueDate))
            .career(parseYearsOfExperienceRange(this.career))
            .collectTime(parseDate(this.collectTime))
            .build();
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr.equals("상시채용")) {
            return LocalDate.of(2100, 1, 1);
        }
        DateTimeFormatter[] formatters = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd"),
            DateTimeFormatter.ofPattern("yy.MM.dd"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy"),
            DateTimeFormatter.ofPattern("dd.MM.yy"),
            DateTimeFormatter.ofPattern("MM.dd.yyyy"),
            DateTimeFormatter.ofPattern("MM.dd.yy"),
            DateTimeFormatter.ofPattern("MM.dd일"),
            DateTimeFormatter.ofPattern("MM.dd일", Locale.KOREA),
            DateTimeFormatter.ofPattern("MM.dd", Locale.KOREA),
            DateTimeFormatter.ofPattern("MM-dd", Locale.KOREA),
            DateTimeFormatter.ofPattern("dd.MM", Locale.KOREA)
        };
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}
