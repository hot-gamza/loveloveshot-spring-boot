package com.loveloveshot.image.command.domain.aggregate.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TBL_IMAGE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageNo;

    @Column
    private String imageName;

    @Column
    private String imagePath;

    @Builder
    public Image(Long imageNo, String imageName, String imagePath) {
        this.imageNo = imageNo;
        this.imageName = imageName;
        this.imagePath = imagePath;
    }
}
