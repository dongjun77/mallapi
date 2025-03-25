package com.project.mallapi.service;

import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.PageResponseDTO;
import com.project.mallapi.dto.ProductDTO;
import com.project.mallapi.dto.ProductListDTO;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public interface ProductService {

    PageResponseDTO<ProductDTO> getList (PageRequestDTO pageRequestDTO);

    Long register(ProductDTO productDTO);

    ProductDTO get(Long pno);

    void modify(ProductDTO productDTO);

    void remove(Long pno);

    List<ProductListDTO> getRecent();
}
