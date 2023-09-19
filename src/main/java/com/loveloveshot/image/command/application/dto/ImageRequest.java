package com.loveloveshot.image.command.application.dto;

import lombok.*;
import org.springframework.core.io.Resource;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageRequest implements Serializable {
    private List<Resource> maleImages;
    private List<Resource> femaleImages;
}
