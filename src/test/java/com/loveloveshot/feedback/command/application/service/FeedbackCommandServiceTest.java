package com.loveloveshot.feedback.command.application.service;

import com.loveloveshot.feedback.command.application.dto.request.FeedbackRequest;
import com.loveloveshot.feedback.command.domain.aggregate.entity.Feedback;
import com.loveloveshot.feedback.command.domain.repository.FeedbackCommandRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class FeedbackCommandServiceTest {
    FeedbackRequest feedbackRequest = new FeedbackRequest();

    @Autowired
    private FeedbackCommandService feedbackCommandService;
    @Autowired
    private FeedbackCommandRepository feedbackCommandRepository;

    @BeforeEach
    public void setUp() {
        Feedback feedback = new Feedback.Builder()
                .feedbackContent("정말 훌륭합니다.")
                .build();

        feedbackRequest.setFeedbackContent(feedback.getFeedbackContent());
    }

    @Test
    @DisplayName("피드백 제출 테스트 : success")
    void submitFeedbackTest() {
        long before = feedbackCommandRepository.count();

        feedbackCommandService.createFeedback(feedbackRequest);

        long after = feedbackCommandRepository.count();

        Assertions.assertEquals(before + 1, after);
    }

//    @Test
//    @DisplayName("특정 피드백 삭제 테스트 : ")
//    void deleteFeedbackByFeedbackNoTest() {
//
//    }
//
//    @Test
//    @DisplayName("피드백 전체 삭제 테스트 : 특정 회원")
//    void deleteFeedbackByUserNoTest() {
//
//    }
//
//    @Test
//    @DisplayName("피드백 전체 삭제 테스트 : 모든 회원")
//    void deleteAllFeedbackTest() {
//
//    }

}
