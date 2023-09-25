package com.loveloveshot.image.query.application.service;

import com.loveloveshot.image.command.application.dto.request.FindRequest;
import com.loveloveshot.image.command.application.dto.response.FindResponse;
import com.loveloveshot.image.command.domain.aggregate.entity.AiImage;
import com.loveloveshot.image.query.domain.repository.ImageQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageQueryService {

    private final ImageQueryRepository imageQueryRepository;

    public List<String> findTaskIds(FindRequest findRequest) {
        List<String> taskIds = imageQueryRepository.findDistinctTaskIdsByUserNo(findRequest.getUserNo());

        return taskIds;
    }

    public List<FindResponse> findAiImagesByTaskId(FindRequest findRequest) {
        List<AiImage> aiImages = imageQueryRepository.findAiImageByTaskId(findRequest.getTaskId());

        List<FindResponse> findResponses = new ArrayList<>();

        for (AiImage aiImage : aiImages) {
            FindResponse findResponse = new FindResponse(aiImage.getImageName(), aiImage.getImagePath(), aiImage.getTaskId());
            findResponses.add(findResponse);
        }
        return findResponses;
    }

    // 일반 Ai 이미지 조회
//    public FindResponse findStandardAiImage(FindRequest findRequest) {
//        AiImage aiImage = imageQueryRepository.findAiImageByTaskId(findRequest.getTaskId());
//        return new FindResponse(aiImage.getImageName()
//                , aiImage.getImagePath()
//                , aiImage.getTaskId());
//    }

//    public Map<String, List<FindResponse>> findAiImageByUserNo(FindRequest findRequest) {
//        List<String> taskIds = imageQueryRepository.findDistinctTaskIdsByUserNo(findRequest.getUserNo());
//
//        Map<String, List<FindResponse>> taskResponsesMap = new HashMap<>();
//
//        for (String taskId : taskIds) {
//            List<AiImage> aiImages = imageQueryRepository.findAiImageByTaskId(taskId);
//
//            List<FindResponse> findResponses = new ArrayList<>();
//
//            for (AiImage aiImage : aiImages) {
//                FindResponse findResponse = new FindResponse(aiImage.getImageName(), aiImage.getImagePath(), aiImage.getTaskId());
//                findResponses.add(findResponse);
//            }
//
//            taskResponsesMap.put(taskId, findResponses);
//        }
//
//        return taskResponsesMap;
//    }
}