package com.loveloveshot.image.query.application.service;

import com.loveloveshot.image.command.application.dto.request.FindRequest;
import com.loveloveshot.image.command.domain.aggregate.entity.AiImage;
import com.loveloveshot.image.command.domain.repository.ImageCommandRepository;
import com.loveloveshot.image.query.domain.repository.ImageQueryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
public class ImageQueryServiceTests {

    @Autowired
    private ImageCommandRepository imageCommandRepository;

    @Autowired
    private ImageQueryRepository imageQueryRepository;

    @Test
    @DisplayName("이미지 조회 기능: userNo와 taskId로 AI 이미지 조회")
    void getImageByUserNoAndTaskId() {

        AiImage aiImage = AiImage.builder()
                .taskId("asd")
                .imagePath("assd")
                .imageName("asdf")
                .userNo(1L)
                .build();

        AiImage aiImage2 = AiImage.builder()
                .taskId("asd")
                .imagePath("assd")
                .imageName("asdf")
                .userNo(1L)
                .build();

        AiImage aiImage3 = AiImage.builder()
                .taskId("fse")
                .imagePath("asddsd")
                .imageName("asdf")
                .userNo(1L)
                .build();

        imageCommandRepository.save(aiImage);
        imageCommandRepository.save(aiImage2);
        imageCommandRepository.save(aiImage3);

        FindRequest findRequest = new FindRequest(1L, "asd");

        List<String> taskIds = imageQueryRepository.findDistinctTaskIdsByUserNo(1L);

        List<AiImage> aiImageList = imageQueryRepository.findAiImageByUserNoAndTaskId(findRequest.getUserNo(), taskIds.get(0));

        for (AiImage image : aiImageList) {
            System.out.println(image);
        }


        Assertions.assertEquals(2, aiImageList.size());
    }
}
