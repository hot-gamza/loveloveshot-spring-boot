package com.loveloveshot.image.command.application.controller;

import com.loveloveshot.image.command.application.dto.RequestImageListDTO;
import com.loveloveshot.image.command.application.dto.RequestSingleImageDTO;
import com.loveloveshot.image.command.application.service.ImageCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ImageCommandController {

    private final ImageCommandService imageCommandService;

    @PostMapping("/singleImage") // 필기. RequestParam에 name값을 지정해주지 않으면 디폴트로 변수명을 key값으로 가짐.
    public void uploadSingleImage(@RequestParam MultipartFile maleSingleImage,
                                  @RequestParam MultipartFile femaleSingleImage,
                                  RequestSingleImageDTO singleImageDTO) {

        singleImageDTO.setMaleSingleImage(maleSingleImage);
        singleImageDTO.setFemaleSingleImage(femaleSingleImage);

        if(maleSingleImage.isEmpty() || femaleSingleImage.isEmpty()) {
            throw new IllegalArgumentException("사진을 첨부해주세요");
        }

        imageCommandService.getSingleImage(singleImageDTO);
    }


    @PostMapping("/imageList")
    public void uploadImageList(@RequestParam List<MultipartFile> maleImageList,
                                @RequestParam List<MultipartFile> femaleImageList,
                                RequestImageListDTO imageListDTO) {

        imageListDTO.setMaleImageList(maleImageList);
        imageListDTO.setFemaleImageList(femaleImageList);

        if(maleImageList.size() < 2 || maleImageList.size() > 20) {
            throw new IllegalArgumentException("2~20장의 사진을 올려주세요");
        }

        imageCommandService.getImageList(imageListDTO);
    }

}
