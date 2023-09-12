package com.loveloveshot.image.command.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaveImageDTO {

    private String originImageName;
    private String savedImageName;
    private String imagePath;
    private Long userNO;
}
