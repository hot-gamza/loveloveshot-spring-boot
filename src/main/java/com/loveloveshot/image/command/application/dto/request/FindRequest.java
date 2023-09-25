package com.loveloveshot.image.command.application.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FindRequest {
    private Long userNo;
    private String taskId;
}
