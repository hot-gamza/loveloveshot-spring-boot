package com.loveloveshot.feedback.query.domain.repository;

import com.loveloveshot.feedback.command.domain.aggregate.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackQueryRepository extends JpaRepository<Feedback, Long> {
}
