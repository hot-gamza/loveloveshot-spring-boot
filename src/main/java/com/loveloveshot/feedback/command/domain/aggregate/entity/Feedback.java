package com.loveloveshot.feedback.command.domain.aggregate.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "TBL_FEEDBACK")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackNo;

    @Column(columnDefinition = "LONGTEXT")
    private String feedbackContent;

//    @Column
//    private Date feedbackDate;
//    @Column
//    private String feedbackTitle;
//    @Embedded
//    private FeedbackWriterVO feedbackWriterVO;

    public Feedback(Builder builder) {
        this.feedbackNo = builder.feedbackNo;
        this.feedbackContent = builder.feedbackContent;
    }

    public static class Builder {
        private Long feedbackNo;
        private String feedbackContent;

        public Builder feedbackNo(Long feedbackNo) {
            this.feedbackNo = feedbackNo;
            return this;
        }

        public Builder feedbackContent(String feedbackContent) {
            this.feedbackContent = feedbackContent;
            return this;
        }

        public Feedback build() {
            return new Feedback(this);
        }

    }
}