package com.loveloveshot.image.command.application.service;

import com.loveloveshot.image.command.application.dto.request.ImageRequest;
import com.loveloveshot.image.command.application.dto.request.SaveRequest;
import com.loveloveshot.image.command.application.dto.response.UploadResponse;
import com.loveloveshot.image.command.domain.aggregate.entity.AiImage;
import com.loveloveshot.image.command.domain.repository.ImageCommandRepository;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageCommandService {

    private final ImageCommandDomainService imageCommandDomainService;
    private final ImageCommandRepository imageCommandRepository;

    public UploadResponse createAISingleImage(Long userNo, ImageRequest imageRequest) {

        UploadResponse aiImageDTO = imageCommandDomainService.uploadStandardImage(imageRequest);

//        String filePath = "C:\\AIImages/";
//
//        File dir = new File(filePath);
//        if (!dir.exists()) {
//            dir.mkdirs();   //폴더 없을 시 자동으로 하위폴더 생성
//        }
//
//        String originFileName = aiImageDTO.getAiImage().getOriginalFilename();  //원본 파일 이름
//        String ext = originFileName.substring(originFileName.lastIndexOf(".") + 1); //파일 확장자
//        String savedName = UUID.randomUUID().toString().replaceAll("-", "") + "." + ext; //저장되는 이름
//
//        try {
//            aiImageDTO.getAiImage().transferTo(new File(filePath + savedName));
//
//            Image image = Image.builder()
//                    .originImageName(originFileName)
//                    .savedImageName(savedName)
//                    .imagePath(filePath)
//                    .userVO(new UserVO(userNo))
//                    .build();
//
//            imageCommandRepository.save(image);
//
//        } catch (IOException e) {
//            new File(filePath + savedName).delete();  //업로드 후 DB저장 중 오류났을 때 업로드된 이미지 삭제해줌
//        }

        return aiImageDTO;
    }

    // 일반 이미지 업로드
    public UploadResponse uploadStandardImage(ImageRequest imageRequest) {
        return imageCommandDomainService.uploadStandardImage(imageRequest);
    }

    // 일반 AI 이미지 저장
    public UploadResponse saveStandardImage(SaveRequest saveRequest) throws IOException {
        AiImage aiImage = AiImage.builder()
                .imageName(saveRequest.getAiImage().getName())
                .imagePath(saveRequest.getAiImage().getPath())
                .taskId(saveRequest.getTaskId())
                .build();
        imageCommandRepository.save(aiImage);
        return new UploadResponse();
    }

    public UploadResponse uploadPremiumImages(ImageRequest imageRequest) {

        return imageCommandDomainService.uploadPremiumImages(imageRequest);
    }

}
