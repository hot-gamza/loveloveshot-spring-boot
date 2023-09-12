package com.loveloveshot.feedback.command.application.controller;

import com.loveloveshot.common.response.ApiResponse;
import com.loveloveshot.feedback.command.application.dto.request.FeedbackRequest;
import com.loveloveshot.feedback.command.domain.aggregate.entity.Feedback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class FeedbackCommandControllerTest {
    FeedbackRequest feedbackRequest = new FeedbackRequest();

    @Autowired
    private FeedbackCommandController feedbackCommandController;

    @BeforeEach
    public void setUp() {
        Feedback feedback = new Feedback.Builder()
                .feedbackContent("정말 훌륭합니다.")
                .build();

        feedbackRequest.setFeedbackContent(feedback.getFeedbackContent());
    }

    @Test
    @DisplayName("피드백 제출 테스트 : success")
    void createFeedbackTest() {
        ApiResponse apiResponse = feedbackCommandController
                .createFeedback(feedbackRequest);

        Assertions.assertEquals("SUCCESS"
                , apiResponse.getStatus().toString());
    }
}