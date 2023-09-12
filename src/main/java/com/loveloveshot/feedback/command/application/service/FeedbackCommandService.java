package com.loveloveshot.feedback.command.application.service;

import com.loveloveshot.feedback.command.application.dto.request.FeedbackRequest;
import com.loveloveshot.feedback.command.application.dto.response.FeedbackResponse;
import com.loveloveshot.feedback.command.domain.aggregate.entity.Feedback;
import com.loveloveshot.feedback.command.domain.repository.FeedbackCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackCommandService {
    private final FeedbackCommandRepository feedbackCommandRepository;

    @Transactional
    public FeedbackResponse createFeedback(FeedbackRequest feedbackRequest) {
        Feedback feedback = new Feedback.Builder()
                .feedbackContent(feedbackRequest.getFeedbackContent())
                .build();

        Feedback savedFeedback = feedbackCommandRepository.saveAndFlush(feedback);

        return new FeedbackResponse(
                savedFeedback.getFeedbackNo(),
                savedFeedback.getFeedbackContent()
        );
    }

}
