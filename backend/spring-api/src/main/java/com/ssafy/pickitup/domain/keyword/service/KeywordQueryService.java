package com.ssafy.pickitup.domain.keyword.service;


import com.ssafy.pickitup.domain.keyword.dto.KeywordResponseDto;
import com.ssafy.pickitup.domain.keyword.entity.Keyword;
import com.ssafy.pickitup.domain.keyword.repository.KeywordQueryJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordQueryService {

    private final KeywordQueryJpaRepository keywordQueryJpaRepository;

    public List<KeywordResponseDto> findAllKeyword() {
        List<Keyword> keywords = keywordQueryJpaRepository.findAll();
        return keywords.stream()
            .map(keyword -> KeywordResponseDto.toDto(keyword))
            .toList();

    }
}
