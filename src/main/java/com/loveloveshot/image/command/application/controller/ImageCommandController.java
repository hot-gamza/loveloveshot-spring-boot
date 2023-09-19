package com.loveloveshot.image.command.application.controller;

import com.loveloveshot.common.response.ApiResponse;
import com.loveloveshot.image.command.application.dto.AiImageRequest;
import com.loveloveshot.image.command.application.dto.ImageRequest;
import com.loveloveshot.image.command.application.service.ImageCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://loveloveshot.com", "http://192.168.0.12:3000", "*"})
public class ImageCommandController {

    private final ImageCommandService imageCommandService;

    // 스탠다드 이미지 업로드
    @PostMapping(value = "/standardImage")
    // 필기. RequestParam에 name값을 지정해주지 않으면 디폴트로 변수명을 key값으로 가짐.
    public ResponseEntity<ApiResponse> uploadStandardImage(@RequestParam MultipartFile maleImage,
                                                           @RequestParam MultipartFile femaleImage,
                                                           ImageRequest imageRequest) {
        List<Resource> maleImages = new ArrayList<>();
        List<Resource> femaleImages = new ArrayList<>();
        maleImages.add(0, maleImage.getResource());
        femaleImages.add(0, femaleImage.getResource());

        imageRequest.setMaleImages(maleImages);
        imageRequest.setFemaleImages(femaleImages);

        if (maleImage.isEmpty() || femaleImage.isEmpty()) {
            throw new IllegalArgumentException("사진을 첨부해주세요");
        }

        // 설명. 파일 확장자 이미지 형식인지 확인
        if (!maleImage.getContentType().startsWith("image") ||
                !femaleImage.getContentType().startsWith("image")) {
            throw new IllegalArgumentException("이미지 형식의 파일을 올려주세요");
        }
        Long userNo = 1L;   //임의 값

        return ResponseEntity.ok(ApiResponse.success("성공적으로 업로드 되었습니다."
                , imageCommandService.uploadStandardImage(imageRequest)));
    }

    // 스탠다드 AI 이미지 저장
    @PostMapping("/saveStandardImage")
    public ResponseEntity<ApiResponse> saveAiImages(@RequestParam byte[] aiImage,
                                                    AiImageRequest aiImageRequest) {
        System.out.println("aiImage = " + aiImage);
        List<String> files = new ArrayList<>();
        String filePath = "src/main/webapp/AiImages/" + UUID.randomUUID() + ".png"; // Ai 이미지 로컬 저장 경로
        try {
            // 바이트 배열을 파일로 저장
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(aiImage);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        files.add(0, filePath);
        System.out.println("files = " + files);
        aiImageRequest.setAiImage(new File(files.get(0)));

        return ResponseEntity.ok(ApiResponse.success("성공적으로 저장 되었습니다."
                , imageCommandService.saveStandardImage(aiImageRequest)));
    }
//
////    @PostMapping("/imageList")
////    public void uploadImageList(@RequestParam List<MultipartFile> maleImageList,
////                                @RequestParam List<MultipartFile> femaleImageList,
////                                ImageListRequestDTO imageListDTO) {
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
