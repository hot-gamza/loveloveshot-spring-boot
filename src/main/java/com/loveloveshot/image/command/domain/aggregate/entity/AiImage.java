package com.loveloveshot.image.command.domain.aggregate.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TBL_AiIMAGE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiImage {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aiImageNo;

    @Column
    private String imageName;

    @Column
    private String imagePath;

    @Builder
    public AiImage(Long aiImageNo, String imageName, String imagePath) {
        this.aiImageNo = aiImageNo;
        this.imageName = imageName;
        this.imagePath = imagePath;
    }
}
