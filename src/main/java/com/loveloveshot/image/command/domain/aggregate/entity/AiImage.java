package com.loveloveshot.image.command.domain.aggregate.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_AI_IMAGE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AiImage {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aiImageNo;

    @Column
    private String taskId;

    @Column
    private String imageName;

    @Column
    private String imagePath;

    @Column
    private Long userNo;

    @Builder
    public AiImage(Long aiImageNo, String taskId, String imageName, String imagePath, Long userNo) {
        this.aiImageNo = aiImageNo;
        this.taskId = taskId;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.userNo = userNo;
    }
}
