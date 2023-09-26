package com.loveloveshot.image.command.application.controller;

import com.loveloveshot.common.response.ApiResponse;
import com.loveloveshot.image.command.application.dto.request.ImageRequest;
import com.loveloveshot.image.command.application.dto.request.SaveRequest;
import com.loveloveshot.image.command.application.dto.response.UploadResponse;
import com.loveloveshot.image.command.application.service.ImageCommandService;
import com.loveloveshot.image.command.application.service.ImageException;
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
@CrossOrigin(origins = "*")
public class ImageCommandController {
    private static int WAITING_NUMBER = 0;
    private final ImageCommandService imageCommandService;
    private final ImageException IMAGE_EXCEPTION = new ImageException();

    // 일반 이미지 업로드
    @PostMapping(value = "/uploadStandardImage")
    public ResponseEntity<ApiResponse> uploadStandardImage(@RequestParam MultipartFile maleImage,
                                                           @RequestParam MultipartFile femaleImage,
                                                           ImageRequest imageRequest) {
        List<Resource> maleImages = new ArrayList<>();
        List<Resource> femaleImages = new ArrayList<>();
        IMAGE_EXCEPTION.standardImageUploadExceptionHandling(maleImage, femaleImage);

        maleImages.add(0, maleImage.getResource());
        femaleImages.add(0, femaleImage.getResource());
        imageRequest.setMaleImageResources(maleImages);
        imageRequest.setFemaleImageResources(femaleImages);
        UploadResponse uploadResponse = imageCommandService.uploadStandardImage(imageRequest);
        WAITING_NUMBER++;
        System.out.println("WAITING_NUMBER = " + WAITING_NUMBER);
        uploadResponse.setWaitingNumber(WAITING_NUMBER);
        return ResponseEntity.ok(ApiResponse.success("success", uploadResponse));
    }

    // 일반 AI 이미지 저장
    @PostMapping("/saveAiImage")
    public ResponseEntity<ApiResponse> saveAiImage(@RequestPart(value = "file", required = false) MultipartFile aiImage,
                                                   @RequestPart(value = "task_id", required = false) String taskId,
                                                   SaveRequest saveRequest) {
        System.out.println("aiImage = " + aiImage);
        System.out.println("taskId = " + taskId);
        List<File> files = new ArrayList<>();

        String filePath = System.getProperty("user.dir") + "/src/main/webapp/standard/"; // Ai 이미지 로컬 저장 경로

        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originFileName = aiImage.getOriginalFilename();
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        String savedName = UUID.randomUUID().toString().replaceAll("-", "") + ext;

        File targetFile = new File(filePath + "/" + savedName); // 저장할 파일 객체 생성
        try {
            aiImage.transferTo(targetFile);
        } catch (IOException e) {
            new File(filePath + "/" + savedName).delete();
        }
        files.add(0, targetFile);
        saveRequest.setAiImage(files);
        saveRequest.setTaskId(taskId);
        WAITING_NUMBER--;
        System.out.println("WAITING_NUMBER = " + WAITING_NUMBER);
        return ResponseEntity.ok(ApiResponse.success("success"
                , imageCommandService.saveStandardImage(saveRequest)));

        //        List<String> files = new ArrayList<>();
//        String filePath = "src/main/webapp/AiImages/" + UUID.randomUUID() + ".png"; // Ai 이미지 로컬 저장 경로
//        try {
//            // 바이트 배열을 파일로 저장
//            FileOutputStream fos = new FileOutputStream(filePath);
//            fos.write(response);
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        files.add(0, filePath);
//        System.out.println("files = " + files);

        // 이미지 AI 서버에서 JSON으로 받는 법
//        JsonElement element = JsonParser.parseString(response);
//        String fileData = element.getAsJsonObject().get("file_data").getAsString();
//
//        // Base64 문자열을 바이트 배열로 변환
//        byte[] imageBytes = Base64.getDecoder().decode(fileData);
//
//        // 바이트 배열을 BufferedImage 객체로 변환
//        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
//            BufferedImage image = ImageIO.read(bis);
//
//            // BufferedImage 객체를 이미지 파일로 저장
//            ImageIO.write(image, "png", new File(filePath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    // 프리미엄 이미지 업로드
    @PostMapping("/uploadPremiumImage")
    public ResponseEntity<ApiResponse> uploadPremiumImages(@RequestParam List<MultipartFile> maleImages,
                                                           @RequestParam List<MultipartFile> femaleImages,
                                                           ImageRequest imageRequest) {
        IMAGE_EXCEPTION.premiumImageUploadExceptionHandling(maleImages, femaleImages);

        imageRequest.setMaleImageResources(getMultipartFile(maleImages));
        imageRequest.setFemaleImageResources(getMultipartFile(femaleImages));
        UploadResponse uploadResponse = imageCommandService.uploadStandardImage(imageRequest);
        uploadResponse.setWaitingNumber(WAITING_NUMBER);
        WAITING_NUMBER++;
        return ResponseEntity.ok(ApiResponse.success("success", uploadResponse));
    }

    private List<Resource> getMultipartFile(List<MultipartFile> images) {
        List<Resource> imageResources = new ArrayList<>();

        for (MultipartFile image : images) {
            imageResources.add(image.getResource());
        }
        System.out.println("imageResources = " + imageResources);
        return imageResources;
    }

    // 프리미엄 AI 이미지 저장
    @PostMapping("/saveAiImages")
    public ResponseEntity<ApiResponse> saveAiImages(
            @RequestPart(value = "files", required = false) List<MultipartFile> aiImages,
            @RequestPart(value = "task_id", required = false) String taskId,
            SaveRequest saveRequest) {
        List<File> files = new ArrayList<>();
        String filePath = System.getProperty("user.dir") + "/src/main/webapp/premium/"; // Ai 이미지 로컬 저장 경로
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (MultipartFile aiImage : aiImages) {
            String originFileName = aiImage.getOriginalFilename();
            String ext = originFileName.substring(originFileName.lastIndexOf("."));
            String savedName = UUID.randomUUID().toString().replaceAll("-", "") + ext;

            File targetFile = new File(filePath + "/" + savedName); // 저장할 파일 객체 생성
            try {
                aiImage.transferTo(targetFile);
            } catch (IOException e) {
                new File(filePath + "/" + savedName).delete();
            }
            files.add(targetFile);
        }
        saveRequest.setAiImage(files);
        saveRequest.setTaskId(taskId);
        WAITING_NUMBER--;

        return ResponseEntity.ok(ApiResponse.success("success"
                , imageCommandService.savePremiumImage(saveRequest)));

        //        List<String> files = new ArrayList<>();
//        String filePath = "src/main/webapp/AiImages/" + UUID.randomUUID() + ".png"; // Ai 이미지 로컬 저장 경로
//        try {
//            // 바이트 배열을 파일로 저장
//            FileOutputStream fos = new FileOutputStream(filePath);
//            fos.write(response);
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        files.add(0, filePath);
//        System.out.println("files = " + files);

        // 이미지 AI 서버에서 JSON으로 받는 법
//        JsonElement element = JsonParser.parseString(response);
//        String fileData = element.getAsJsonObject().get("file_data").getAsString();
//
//        // Base64 문자열을 바이트 배열로 변환
//        byte[] imageBytes = Base64.getDecoder().decode(fileData);
//
//        // 바이트 배열을 BufferedImage 객체로 변환
//        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
//            BufferedImage image = ImageIO.read(bis);
//
//            // BufferedImage 객체를 이미지 파일로 저장
//            ImageIO.write(image, "png", new File(filePath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
