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

        String filePath = System.getProperty("user.dir") + "/src/main/webapp/AiImages/"; // Ai 이미지 로컬 저장 경로

        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originFileName = aiImage.getOriginalFilename();
        System.out.println("originFileName = " + originFileName);
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        System.out.println("ext = " + ext);
        String savedName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
        System.out.println("savedName = " + savedName);

        File targetFile = new File(filePath + "/" + savedName); // 저장할 파일 객체 생성
        try {
            aiImage.transferTo(targetFile);
        } catch (IOException e) {
            new File(filePath + "/" + savedName).delete();
        }
        files.add(0, targetFile);
        System.out.println("targetFile = " + targetFile);
        saveRequest.setAiImage(targetFile);
        saveRequest.setTaskId(taskId);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 저장 되었습니다."
                , imageCommandService.saveStandardImage(saveRequest)));
    }

    // 프리미엄 이미지 업로드
    @PostMapping("/uploadPremiumImage")
    public ResponseEntity<ApiResponse> uploadImageList(@RequestParam List<MultipartFile> maleImages,
                                                       @RequestParam List<MultipartFile> femaleImages,
                                                       ImageRequest imageRequest) {
        List<Resource> maleImageResources = getMultipartFile(maleImages);
        List<Resource> femaleImageResources = getMultipartFile(femaleImages);

        imageRequest.setMaleImages(maleImageResources);
        imageRequest.setFemaleImages(femaleImageResources);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 업로드 되었습니다."
                , imageCommandService.uploadPremiumImages(imageRequest)));
    }

    private List<Resource> getMultipartFile(List<MultipartFile> images) {
        List<Resource> imageResources = new ArrayList<>();

        for (MultipartFile image : images) {
            imageResources.add(image.getResource());
        }
        return imageResources;
    }
}
