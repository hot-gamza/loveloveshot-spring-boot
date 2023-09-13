package com.loveloveshot.image.command.infrastructure.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loveloveshot.common.annotation.InfraService;
import com.loveloveshot.image.command.application.dto.AIImageResponseDTO;
import com.loveloveshot.image.command.application.dto.SingleImageRequestDTO;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@InfraService
public class ImageCommandInfraService implements ImageCommandDomainService {

    @Override
    public AIImageResponseDTO getAISingleImage(SingleImageRequestDTO singleImageDTO) {

        System.out.println(singleImageDTO.getMaleSingleImage().getOriginalFilename());

        String reqURL = ""; // AI 단독 이미지 생성 API URL

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");


            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder result = new StringBuilder();
            String line = "";

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result); // 응답

            // Gson 라이브러리로 JSON 파싱
            JsonElement element = JsonParser.parseString(result.toString());

            MultipartFile aiImage = (MultipartFile) element;

            return new AIImageResponseDTO(aiImage);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

//    @Override
//    public void getAIImageList(ImageListRequestDTO imageListDTO) {
//        System.out.println(imageListDTO.getMaleImageList().get(0).getOriginalFilename());
//    }

}
