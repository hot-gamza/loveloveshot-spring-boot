package com.loveloveshot.feedback.command.application.controller;

import com.loveloveshot.common.response.ApiResponse;
import com.loveloveshot.feedback.command.application.dto.request.FeedbackRequest;
import com.loveloveshot.feedback.command.application.service.FeedbackCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vi/feedbacks")
public class FeedbackCommandController {
    private final FeedbackCommandService feedbackCommandService;

    @PostMapping
    public ApiResponse createFeedback(FeedbackRequest feedbackRequest) {
        return ApiResponse.success("성공적으로 제출되었습니다."
                , feedbackCommandService.createFeedback(feedbackRequest));
    }
}