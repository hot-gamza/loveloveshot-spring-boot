package com.loveloveshot.feedback.query.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class FeedbackQueryServiceTest {
    @Autowired
    private FeedbackQueryService feedbackQueryService;

//    @Test
//    @DisplayName("피드백 전체 조회 테스트 : 특정 회원")
//    public void findAllFeedbackByUserNoTest() {
//
//    }
//
//    @Test
//    @DisplayName("피드백 전체 조회 테스트 : 모든 회원")
//    public void findAllFeedbackTest() {
//
//    }
}
