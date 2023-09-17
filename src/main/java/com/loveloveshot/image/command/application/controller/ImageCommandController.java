package com.loveloveshot.image.command.application.controller;

import com.loveloveshot.common.response.ApiResponse;
import com.loveloveshot.image.command.application.dto.SingleImageRequest;
import com.loveloveshot.image.command.application.dto.ImageListRequest;
import com.loveloveshot.image.command.application.service.ImageCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ImageCommandController {

    private final ImageCommandService imageCommandService;

    @PostMapping("/singleImage") // 필기. RequestParam에 name값을 지정해주지 않으면 디폴트로 변수명을 key값으로 가짐.
    public ResponseEntity<ApiResponse> uploadSingleImage(@RequestParam MultipartFile maleSingleImage,
                                                         @RequestParam MultipartFile femaleSingleImage,
                                                         SingleImageRequest singleImageDTO) {

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

        return ResponseEntity.ok(ApiResponse.success("성공적으로 등록되었습니다."
                , imageCommandService.createAiSingleImage(userNo, singleImageDTO)));
    }

    @PostMapping("/imageList")
    public ResponseEntity<ApiResponse> uploadImageList(@RequestParam List<MultipartFile> maleImageList,
                                @RequestParam List<MultipartFile> femaleImageList,
                                ImageListRequest imageList) {

        imageList.setMaleImageList(maleImageList);
        imageList.setFemaleImageList(femaleImageList);

        if(maleImageList.size() < 2 || maleImageList.size() > 20) {
            throw new IllegalArgumentException("2~20장의 사진을 올려주세요");
        }

        if(femaleImageList.size() < 2 || femaleImageList.size() > 20) {
            throw new IllegalArgumentException("2~20장의 사진을 올려주세요");
        }

        for (MultipartFile maleImage : maleImageList) {
            if (!maleImage.getContentType().startsWith("image")) {
                throw new IllegalArgumentException("이미지 형식의 파일을 올려주세요");
            }
        }

        for (MultipartFile femaleImage : femaleImageList) {
            if(!femaleImage.getContentType().startsWith("image")) {
                throw new IllegalArgumentException("이미지 형식의 파일을 올려주세요");
            }
        }
        Long userNo = 1L;

        return ResponseEntity.ok(ApiResponse.success("성공적으로 등록되었습니다.",
                imageCommandService.createAiImageList(userNo, imageList)));
    }
}
