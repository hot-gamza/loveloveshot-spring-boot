package com.loveloveshot.image.query.domain.repository;

import com.loveloveshot.image.command.domain.aggregate.entity.AiImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageQueryRepository extends JpaRepository<AiImage, Long> {
    AiImage findAiImageByTaskId(String taskId);
}
