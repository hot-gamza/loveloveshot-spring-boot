package com.loveloveshot.feedback.command.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {
    private Long feedbackNo;
    private String feedbackContent;
    
//    private Date feedbackDate;
//    private String feedbackTitle;
//    private FeedbackWriterVO feedbackWriterVO;
}
