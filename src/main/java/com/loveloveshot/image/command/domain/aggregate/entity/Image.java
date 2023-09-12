package com.loveloveshot.image.command.domain.aggregate.entity;

import com.loveloveshot.image.command.domain.aggregate.vo.UserVO;
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
    private String originImageName;

    @Column
    private String savedImageName;

    @Column
    private String imagePath;

    @Embedded
    private UserVO userVO;

    @Builder
    public Image(Long imageNo, String originImageName, String savedImageName, String imagePath, UserVO userVO) {
        this.imageNo = imageNo;
        this.originImageName = originImageName;
        this.savedImageName = savedImageName;
        this.imagePath = imagePath;
        this.userVO = userVO;
    }
}
