package com.loveloveshot.image.command.infrastructure.service;

import com.loveloveshot.common.annotation.InfraService;
import com.loveloveshot.image.command.application.dto.AIImageResponseDTO;
import com.loveloveshot.image.command.application.dto.ImagesDTO;
import com.loveloveshot.image.command.application.dto.SingleImageRequestDTO;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@InfraService
public class ImageCommandInfraService implements ImageCommandDomainService {
    /**
     * Map을 jsonString으로 변환
     */
    @SuppressWarnings("unchecked")
    public static String getJsonStringFromMap(Map<String, Object> map) {

        JSONObject json = new JSONObject();

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            String key = entry.getKey();
            Object value = entry.getValue();

            json.put(key, value);
        }

        return json.toJSONString();
    }

    @Override
    public AIImageResponseDTO getAISingleImage(SingleImageRequestDTO singleImageDTO, ImagesDTO imagesDTO) {
        System.out.println("imagesDTO1 = " + imagesDTO.getMaleImage());
        System.out.println("imagesDTO2 = " + imagesDTO.getFemaleImage());

        String reqURL = "http://192.168.0.40:5001/"; // AI 단독 이미지 생성 API URL

        try {
            // Set header
            Map<String, String> headers = new HashMap<>();
            HttpPostMultipart multipart = new HttpPostMultipart(reqURL, "utf-8", headers);
            // Add form field
//            multipart.addFormField("username", "test_name");
//            multipart.addFormField("password", "test_psw");
            // Add file
            multipart.addFilePart("imgFile", new File(imagesDTO.getMaleImage()));
            multipart.addFilePart("imgFile", new File(imagesDTO.getFemaleImage()));
            // Print result
            String response = multipart.finish();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            URL url = new URL(reqURL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
////            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + this.boundary);
//            conn.setDoOutput(true);
//
//            Map<String, Object> requestMap = new HashMap<String, Object>();
//            System.out.println(singleImageDTO.getMaleSingleImage());
//            System.out.println(singleImageDTO.getFemaleSingleImage());
//
//            requestMap.put("male", singleImageDTO.getMaleSingleImage());
//            requestMap.put("female", singleImageDTO.getFemaleSingleImage());
//
//            String requestBody = getJsonStringFromMap(requestMap);
//            System.out.println("requestBody:" + requestBody);
//
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//            bw.write(requestBody);
//            bw.flush();
//            bw.close();
//
//            System.out.println("getResponseCode():" + conn.getResponseCode());
//            System.out.println("getResponseMessage():" + conn.getResponseMessage());
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            StringBuilder result = new StringBuilder();
//            String line = "";
//
//            while ((line = br.readLine()) != null) {
//                result.append(line);
//            }
//            System.out.println("response body : " + result); // 응답
//
//            // Gson 라이브러리로 JSON 파싱
//            JsonElement element = JsonParser.parseString(result.toString());
//
//            MultipartFile aiImage = (MultipartFile) element;
//
//            return new AIImageResponseDTO(aiImage);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

//    @Override
//    public void getAIImageList(ImageListRequestDTO imageListDTO) {
//        System.out.println(imageListDTO.getMaleImageList().get(0).getOriginalFilename());
//    }

}
