package com.loveloveshot.image.command.application.controller;

import com.loveloveshot.common.response.ApiResponse;
import com.loveloveshot.image.command.application.dto.request.ImageRequest;
import com.loveloveshot.image.command.application.dto.request.SaveRequest;
import com.loveloveshot.image.command.application.service.ImageCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    // 일반 이미지 업로드
    @PostMapping(value = "/uploadStandardImage")
    public ResponseEntity<ApiResponse> uploadStandardImage(@RequestParam MultipartFile maleImage,
                                                           @RequestParam MultipartFile femaleImage,
                                                           ImageRequest imageRequest) {
        List<Resource> maleImages = new ArrayList<>();
        List<Resource> femaleImages = new ArrayList<>();

        try {
            if (maleImage.isEmpty() || femaleImage.isEmpty()) {
                throw new NullPointerException("사진을 첨부해 주세요");
            }
            if (!maleImage.getContentType().startsWith("image") ||
                    !femaleImage.getContentType().startsWith("image")) {
                throw new IllegalArgumentException("이미지 형식의 파일을 올려주세요");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        maleImages.add(0, maleImage.getResource());
        femaleImages.add(0, femaleImage.getResource());
        imageRequest.setMaleImages(maleImages);
        imageRequest.setFemaleImages(femaleImages);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 업로드 되었습니다."
                , imageCommandService.uploadStandardImage(imageRequest)));
    }

    // 일반 AI 이미지 저장
    @PostMapping("/saveAiImage")
    public ResponseEntity<ApiResponse> saveAiImages(@RequestPart(value = "file", required = false) MultipartFile aiImage,
                                                    @RequestPart(value = "task_id", required = false) String taskId,
                                                    SaveRequest saveRequest) throws IOException {
        System.out.println("aiImage = " + aiImage);
        System.out.println("taskId = " + taskId);
        List<File> files = new ArrayList<>();
        String filePath = "src/main/webapp/AiImages/" + UUID.randomUUID() + ".png"; // Ai 이미지 로컬 저장 경로

        File targetFile = new File("src/main/webapp/AiImages/" + aiImage.getOriginalFilename() + ".png"); // 저장할 파일 객체 생성
        aiImage.transferTo(targetFile); // 파일 저장
        files.add(0, targetFile);
        System.out.println("targetFile = " + targetFile);
        saveRequest.setAiImage(targetFile);
        saveRequest.setTaskId(taskId);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 저장 되었습니다."
                , imageCommandService.saveStandardImage(saveRequest)));
    }


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
