package com.loveloveshot.image.command.application.service;

import com.loveloveshot.image.command.application.dto.AiImageResponse;
import com.loveloveshot.image.command.application.dto.SingleImageRequest;
import com.loveloveshot.image.command.domain.aggregate.entity.Image;
import com.loveloveshot.image.command.domain.aggregate.vo.UserVO;
import com.loveloveshot.image.command.domain.repository.ImageCommandRepository;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageCommandService {

    private final ImageCommandDomainService imageCommandDomainService;
    private final ImageCommandRepository imageCommandRepository;


    public AiImageResponse createAISingleImage(Long userNo, SingleImageRequest singleImageDTO) {

        AiImageResponse aiImageDTO = imageCommandDomainService.getAISingleImage(singleImageDTO);

        String filePath = "C:\\AIImages/";

        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();   //폴더 없을 시 자동으로 하위폴더 생성
        }

        String originFileName = aiImageDTO.getAiImage().getOriginalFilename();  //원본 파일 이름
        String ext = originFileName.substring(originFileName.lastIndexOf(".") + 1); //파일 확장자
        String savedName = UUID.randomUUID().toString().replaceAll("-", "") + "." + ext; //저장되는 이름

        try {
            aiImageDTO.getAiImage().transferTo(new File(filePath + savedName));

            Image image = Image.builder()
                    .originImageName(originFileName)
                    .savedImageName(savedName)
                    .imagePath(filePath)
                    .userVO(new UserVO(userNo))
                    .build();

            imageCommandRepository.save(image);

        } catch (IOException e) {
            new File(filePath + savedName).delete();  //업로드 후 DB저장 중 오류났을 때 업로드된 이미지 삭제해줌
        }

        return aiImageDTO;
    }

    public List<AIImageResponseDTO> createAIImageList(Long userNo, ImageListRequestDTO imageListDTO) {

        List<AIImageResponseDTO> aiImageListDTO = imageCommandDomainService.getAIImageList(imageListDTO);

        String filePath = "C:\\AIImages/";

        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();   //폴더 없을 시 자동으로 하위폴더 생성
        }

        for (AIImageResponseDTO aiImageDTO : aiImageListDTO) {

            String originFileName = aiImageDTO.getAiImage().getOriginalFilename();  //원본 파일 이름
            String ext = originFileName.substring(originFileName.lastIndexOf(".") + 1); //파일 확장자
            String savedName = UUID.randomUUID().toString().replaceAll("-", "") + "." + ext; //저장되는 이름

            try {
                aiImageDTO.getAiImage().transferTo(new File(filePath + savedName));

                Image image = Image.builder()
                        .originImageName(originFileName)
                        .savedImageName(savedName)
                        .imagePath(filePath)
                        .userVO(new UserVO(userNo))
                        .build();

                imageCommandRepository.save(image);

            } catch (IOException e) {
                new File(filePath + savedName).delete();  //업로드 후 DB저장 중 오류났을 때 업로드된 이미지 삭제해줌
            }
        }

        return aiImageListDTO;
    }
}
