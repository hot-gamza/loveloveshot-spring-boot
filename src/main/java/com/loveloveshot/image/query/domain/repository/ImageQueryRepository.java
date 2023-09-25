package com.loveloveshot.image.query.domain.repository;

import com.loveloveshot.image.command.domain.aggregate.entity.AiImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageQueryRepository extends JpaRepository<AiImage, Long> {
    List<AiImage> findAiImageByTaskId(String taskId);

    @Query(value = "SELECT DISTINCT a.taskId FROM AiImage a WHERE a.userNo = :userNo")
    List<String> findDistinctTaskIdsByUserNo(Long userNo);

    List<AiImage> findAiImageByUserNoAndTaskId(Long userNo, String taskId);
}
