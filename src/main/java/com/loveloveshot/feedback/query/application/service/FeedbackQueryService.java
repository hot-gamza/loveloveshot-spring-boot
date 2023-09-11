package com.loveloveshot.feedback.query.application.service;

import com.loveloveshot.feedback.query.domain.repository.FeedbackQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackQueryService {
    private final FeedbackQueryRepository feedbackQueryRepository;

//    // 특정 회원 피드백 전체 조회
//    @Transactional(readOnly = true)
//    public FeedbackResponse findAllByUserNo() {
//
//        return null;
//    }
//
//    // 모든 회원 피드백 전체 조회
//    @Transactional(readOnly = true)
//    public FeedbackResponse findAll() {
//
//        return null;
//    }

}
