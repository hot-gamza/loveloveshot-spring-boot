package com.loveloveshot.image.command.application.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageException {
    public void standardImageUploadExceptionHandling(MultipartFile maleImage, MultipartFile femaleImage) {
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
    }

    public void premiumImageUploadExceptionHandling(List<MultipartFile> maleImages, List<MultipartFile> femaleImages) {
        try {
            if (maleImages.size() < 2 || maleImages.size() > 20) {
                throw new IllegalArgumentException("2~20장의 사진을 올려주세요");
            }
            if (femaleImages.size() < 2 || femaleImages.size() > 20) {
                throw new IllegalArgumentException("2~20장의 사진을 올려주세요");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
