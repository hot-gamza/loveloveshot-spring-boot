package com.loveloveshot.image.command.domain.aggregate.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TBL_AI_IMAGE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiImage {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aiImageNo;

    @Column
    private Long userNo;

    @Column
    private String taskId;

    @Column
    private String imageName;

    @Column
    private String imagePath;

    @Column
    private String grade;

    @Builder
    public AiImage(Long aiImageNo, Long userNo, String taskId, String imageName, String imagePath, String grade) {
        this.aiImageNo = aiImageNo;
        this.userNo = userNo;
        this.taskId = taskId;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.grade = grade;
    }
}