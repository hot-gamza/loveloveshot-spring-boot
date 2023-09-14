package com.loveloveshot.image.command.application.controller;

import com.loveloveshot.common.response.ApiResponse;
import com.loveloveshot.image.command.application.dto.ImagesDTO;
import com.loveloveshot.image.command.application.dto.SingleImageRequestDTO;
import com.loveloveshot.image.command.application.service.ImageCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ImageCommandController {

    private final ImageCommandService imageCommandService;

    @PostMapping("/singleImage") // 필기. RequestParam에 name값을 지정해주지 않으면 디폴트로 변수명을 key값으로 가짐.
    public ApiResponse uploadSingleImage(@RequestParam MultipartFile maleSingleImage,
                                         @RequestParam MultipartFile femaleSingleImage,
                                         SingleImageRequestDTO singleImageDTO) {

        System.out.println("maleSingleImage.getName() = " + maleSingleImage.getName());
        System.out.println("femaleSingleImage = " + femaleSingleImage.getName());
        singleImageDTO.setMaleSingleImage(maleSingleImage);
        singleImageDTO.setFemaleSingleImage(femaleSingleImage);

        if (maleSingleImage.isEmpty() || femaleSingleImage.isEmpty()) {
            throw new IllegalArgumentException("사진을 첨부해주세요");
        }

        // 설명. 파일 확장자 이미지 형식인지 확인
        if (!maleSingleImage.getContentType().startsWith("image") ||
                !femaleSingleImage.getContentType().startsWith("image")) {
            throw new IllegalArgumentException("이미지 형식의 파일을 올려주세요");
        }

        Long userNo = 1L;   //임의 값
//
//        String filePath = "C:\\originalAiImages/";
//
//        File dir = new File(filePath);
//        if (!dir.exists()) {
//            dir.mkdirs();   //폴더 없을 시 자동으로 하위폴더 생성
//        }
//
//        String originFileName1 = maleSingleImage.getOriginalFilename();  //원본 파일 이름
//        String ext1 = originFileName1.substring(originFileName1.lastIndexOf(".") + 1); //파일 확장자
//        String savedName1 = UUID.randomUUID().toString().replaceAll("-", "") + "." + ext1; //저장되는 이름
//
//        String originFileName2 = maleSingleImage.getOriginalFilename();  //원본 파일 이름
//        String ext2 = originFileName2.substring(originFileName2.lastIndexOf(".") + 1); //파일 확장자
//        String savedName2 = UUID.randomUUID().toString().replaceAll("-", "") + "." + ext2; //저장되는 이름
//
//        System.out.println("filePath = " + filePath + savedName1);
//        System.out.println("filePath = " + filePath + savedName2);
//
//        try {
//            maleSingleImage.transferTo(new File(filePath + savedName1));
//            femaleSingleImage.transferTo(new File(filePath + savedName2));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        ImagesDTO imagesDTO = new ImagesDTO();
//        imagesDTO.setMaleImage(filePath + savedName1);
//        imagesDTO.setFemaleImage(filePath + savedName2);

        return ApiResponse.success("성공적으로 등록되었습니다."
                , imageCommandService.createAISingleImage(userNo, singleImageDTO));
    }


//    @PostMapping("/imageList")
//    public void uploadImageList(@RequestParam List<MultipartFile> maleImageList,
//                                @RequestParam List<MultipartFile> femaleImageList,
//                                ImageListRequestDTO imageListDTO) {
//
//        imageListDTO.setMaleImageList(maleImageList);
//        imageListDTO.setFemaleImageList(femaleImageList);
//
//        if(maleImageList.size() < 2 || maleImageList.size() > 20) {
//            throw new IllegalArgumentException("2~20장의 사진을 올려주세요");
//        }
//
//        if(femaleImageList.size() < 2 || femaleImageList.size() > 20) {
//            throw new IllegalArgumentException("2~20장의 사진을 올려주세요");
//        }
//
//        imageCommandService.transferImageList(imageListDTO);
//    }
}
