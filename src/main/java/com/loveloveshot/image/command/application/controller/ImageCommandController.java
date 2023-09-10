package com.loveloveshot.image.command.application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ImageCommandController {

    @PostMapping("/images")   // 필기. RequestParam에 name값을 지정해주지 않으면 디폴트로 변수명을 key값으로 가짐.
    public void uploadImage(@RequestParam List<MultipartFile> imageFileList) {


    }
}
