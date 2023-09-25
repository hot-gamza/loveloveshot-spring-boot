package com.loveloveshot.image.command.application.dto.request;

import lombok.*;
import org.springframework.core.io.Resource;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageRequest {
    private List<Resource> maleImageResources;
    private List<Resource> femaleImageResources;
}
