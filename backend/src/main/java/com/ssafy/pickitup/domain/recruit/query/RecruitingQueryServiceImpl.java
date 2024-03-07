package com.ssafy.pickitup.domain.recruit.query;

import com.ssafy.pickitup.domain.recruit.command.RecruitingCommandService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitingQueryServiceImpl implements RecruitingQueryService {

    private final RecruitingCommandService recruitingCommandService;

    @Override
    public void readKeywords() {
        try {
            ClassPathResource resource = new ClassPathResource("keywords.txt");
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                recruitingCommandService.searchByKeyword(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            log.error("File 'keywords.txt' not found.", e);
        } catch (IOException e) {
            log.error("An error occurred while reading keywords.", e);
        }
    }
}
