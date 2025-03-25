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
public class ProductListDTO {

    private Long pno;

    private String pname;

    private int price;

    private String pdesc;

    private String imageFile;

    public ProductListDTO(Long pno, String pname, int price, String pdesc, String imageFile) {
        this.pno = pno;
        this.pname = pname;
        this.price = price;
        this.pdesc = pdesc;
        this.imageFile = imageFile;
    }
}
