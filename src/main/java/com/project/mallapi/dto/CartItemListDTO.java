package com.project.mallapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CartItemListDTO {

    private Long cino;

    private int qty;

    private String pname;

    private int price;

    private String imageFile;

    public CartItemListDTO(Long cino, int qty, String pname,int price,  String imageFile) {
        this.price = price;
        this.cino = cino;
        this.qty = qty;
        this.pname = pname;
        this.imageFile = imageFile;
    }
}
