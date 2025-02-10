package com.project.mallapi.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long pno;

    private String pname;

    private int price;

    private String pdesc;

    private boolean defFlag;

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>(); //

    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>(); // 파일의 이름들

}
